package com.menus.checout.service.impl;

import com.menus.checout.aop.LogElapsedTime;
import com.menus.checout.constants.Currency;
import com.menus.checout.constants.PaymentMethod;
import com.menus.checout.constants.Status;
import com.menus.checout.constants.ShippingMethod;
import com.menus.checout.dto.CheckoutRequest;
import com.menus.checout.dto.OrderDto;
import com.menus.checout.dto.PaymentRequest;
import com.menus.checout.dto.PaymentResponse;
import com.menus.checout.model.Cart;
import com.menus.checout.model.Order;
import com.menus.checout.model.OrderItem;
import com.menus.checout.model.Transaction;
import com.menus.checout.repository.OrderRepository;
import com.menus.checout.service.CartService;
import com.menus.checout.service.CartValidationService;
import com.menus.checout.service.OrderService;
import com.menus.checout.service.PaymentService;
import com.menus.checout.service.PaymentServiceFactory;
import com.menus.checout.util.ModelMapperUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartValidationService cartValidationService;
    private final PaymentServiceFactory paymentServiceFactory;

    @LogElapsedTime
    @Transactional
    @Override
    public OrderDto addOrder(Long customerId, CheckoutRequest request) throws Exception {
        final Cart cart = cartService.getCart(customerId);

        cartValidationService.validateCart(cart);

        Order order = createOrder(request, cart);

        if (request.getPaymentMethod() != PaymentMethod.COD) {
            payByCard(order, request.getCardToken());
        }

        reserveStockQuantity(order);

        cartService.removeCartItems(cart);

        return ModelMapperUtil.map(orderRepository.save(order), OrderDto.class);
    }

    private void payByCard(Order order, String cardToken) throws Exception {
        PaymentService paymentService = paymentServiceFactory
            .getPaymentService(order.getPaymentMethod());

        PaymentRequest paymentRequest = PaymentRequest.builder()
            .currency(Currency.EGP)
            .transactionAmount(order.getPaidAmount().multiply(BigDecimal.valueOf(100)).intValue())
            .cardToken(cardToken)
            .build();

        PaymentResponse paymentResponse = paymentService.pay(paymentRequest);

        Transaction transaction = Transaction.builder()
            .amount(order.getPaidAmount())
            .cardToken(cardToken)
            .refunded(paymentResponse.isRefunded())
            .paid(paymentResponse.isPaid())
            .order(order)
            .paymentRequestId(paymentResponse.getRequestId())
            .status(paymentResponse.getStatus())
            .currency(paymentResponse.getCurrency())
            .build();

        order.setTransaction(transaction);
    }

    private void reserveStockQuantity(Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.getProduct().decreaseQuantity(
            orderItem.getQuantity()));
    }

    private Order createOrder(CheckoutRequest request, Cart cart) {
        BigDecimal customerBalance = cart.getCustomer().getStoreBalance();

        BigDecimal codFees = getCodFees(request);

        BigDecimal shippingFees = getShippingFees(request);

        BigDecimal vatFees = getVatFees(cart.getSubTotal());

        BigDecimal orderTotalAmount = getOrderTotalAmount(codFees, shippingFees, vatFees,
            cart.getSubTotal());

        BigDecimal paidAmount = orderTotalAmount;
        BigDecimal usedCustomerBalance = BigDecimal.ZERO;

        if (request.isUseCustomerBalance()) {
            if (customerBalance.compareTo(orderTotalAmount) >= 0) {
                paidAmount = BigDecimal.ZERO;
                usedCustomerBalance = BigDecimal.valueOf(orderTotalAmount.doubleValue());
                customerBalance = customerBalance.subtract(orderTotalAmount);
            } else {
                paidAmount = orderTotalAmount.subtract(customerBalance);
                usedCustomerBalance = BigDecimal.valueOf(customerBalance.doubleValue());
                customerBalance = BigDecimal.ZERO;
            }
        }

        cart.getCustomer().setStoreBalance(customerBalance);

        final Order order = Order.builder()
            .codFees(codFees)
            .shippingFees(shippingFees)
            .vatFees(vatFees)
            .customer(cart.getCustomer())
            .paymentMethod(request.getPaymentMethod())
            .subTotalPrice(cart.getSubTotal())
            .totalPrice(orderTotalAmount)
            .useCustomerBalance(request.isUseCustomerBalance())
            .usedCustomerBalance(usedCustomerBalance)
            .paidAmount(paidAmount)
            .shippingMethod(request.getShippingMethod())
            .status(Status.APPROVED)
            .build();

        List<OrderItem> orderItems = getOrderItems(cart, order);

        order.setOrderItems(orderItems);

        return order;

    }

    private List<OrderItem> getOrderItems(Cart cart, Order order) {
        return cart.getCartItems()
            .stream()
            .map(cartItem -> new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity()))
            .collect(Collectors.toList());
    }

    private BigDecimal getOrderTotalAmount(BigDecimal... fees) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal fee : fees) {
            total = total.add(fee);
        }
        return total.setScale(2);
    }

    private BigDecimal getVatFees(BigDecimal totalAmount) {
        return totalAmount.multiply(BigDecimal.valueOf(0.14)).setScale(2);
    }

    private BigDecimal getShippingFees(CheckoutRequest request) {
        if (request.getShippingMethod() == ShippingMethod.DOOR_DELIVERY) {
            return BigDecimal.valueOf(26);
        } else if (request.getShippingMethod() == ShippingMethod.PICKUP_STATION) {
            return BigDecimal.valueOf(5);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getCodFees(CheckoutRequest request) {
        if (request.getPaymentMethod() == PaymentMethod.COD) {
            return BigDecimal.valueOf(10);
        }
        return BigDecimal.ZERO;
    }
}
