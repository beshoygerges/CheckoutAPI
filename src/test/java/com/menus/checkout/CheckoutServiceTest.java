package com.menus.checkout;

import com.menus.checkout.constants.PaymentMethod;
import com.menus.checkout.constants.ShippingMethod;
import com.menus.checkout.dto.CheckoutRequest;
import com.menus.checkout.dto.PaymentResponse;
import com.menus.checkout.exception.*;
import com.menus.checkout.model.*;
import com.menus.checkout.repository.OrderRepository;
import com.menus.checkout.service.PaymentService;
import com.menus.checkout.service.PaymentServiceFactory;
import com.menus.checkout.service.impl.CartServiceImpl;
import com.menus.checkout.service.impl.CheckoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

    @Mock
    private CartServiceImpl cartService;

    @Mock
    private PaymentServiceFactory paymentServiceFactory;

    @Mock
    private PaymentService paymentService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CheckoutServiceImpl orderService;

    @Test
    public void checkoutCodDoorDeliveryShouldPass() throws Exception {
        Long customerId = 1l;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setStoreBalance(BigDecimal.valueOf(10));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        Cart cart = new Cart();
        cart.setId(1l);
        cart.setSubTotal(BigDecimal.valueOf(1000));
        cart.setCustomer(customer);

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        given(cartService.getCart(customerId)).willReturn(cart);
        given(orderRepository.save(any(Order.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Order order = orderService.checkout(customerId, request);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().size()).isEqualTo(cart.getCartItems().size());
        assertThat(order.getCodFees()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getShippingFees()).isEqualTo(BigDecimal.valueOf(26));
        assertThat(order.getTransaction()).isNull();
        assertThat(order.getVatFees()).isEqualTo(order.getSubTotalPrice().multiply(BigDecimal.valueOf(0.14)));
        assertThat(customer.getStoreBalance()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getPaidAmount()).isEqualTo(order.getTotalPrice());
        assertThat(order.getUsedCustomerBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.isUseCustomerBalance()).isEqualTo(false);
        assertThat(order.getTotalPrice()).isEqualTo(
                order.getSubTotalPrice()
                        .add(order.getCodFees())
                        .add(order.getVatFees())
                        .add(order.getShippingFees()));
    }

    @Test
    public void checkoutCodPickupStationShouldPass() throws Exception {
        Long customerId = 1l;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setStoreBalance(BigDecimal.valueOf(10));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.PICKUP_STATION);
        request.setUseCustomerBalance(false);

        Cart cart = new Cart();
        cart.setId(1l);
        cart.setSubTotal(BigDecimal.valueOf(1000));
        cart.setCustomer(customer);

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        given(cartService.getCart(customerId)).willReturn(cart);
        given(orderRepository.save(any(Order.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Order order = orderService.checkout(customerId, request);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().size()).isEqualTo(cart.getCartItems().size());
        assertThat(order.getCodFees()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getShippingFees()).isEqualTo(BigDecimal.valueOf(5));
        assertThat(order.getTransaction()).isNull();
        assertThat(order.getVatFees()).isEqualTo(order.getSubTotalPrice().multiply(BigDecimal.valueOf(0.14)));
        assertThat(customer.getStoreBalance()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getPaidAmount()).isEqualTo(order.getTotalPrice());
        assertThat(order.getUsedCustomerBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.isUseCustomerBalance()).isEqualTo(false);
        assertThat(order.getTotalPrice()).isEqualTo(
                order.getSubTotalPrice()
                        .add(order.getCodFees())
                        .add(order.getVatFees())
                        .add(order.getShippingFees()));
    }

    @Test
    public void checkoutCODUsingCustomerBalanceShouldPass() throws Exception {
        Long customerId = 1l;
        BigDecimal customerBalance = BigDecimal.valueOf(10).setScale(2);

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setStoreBalance(customerBalance);

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(true);

        Cart cart = new Cart();
        cart.setId(1l);
        cart.setSubTotal(BigDecimal.valueOf(1000));
        cart.setCustomer(customer);

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        given(cartService.getCart(customerId)).willReturn(cart);
        given(orderRepository.save(any(Order.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Order order = orderService.checkout(customerId, request);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().size()).isEqualTo(cart.getCartItems().size());
        assertThat(order.getCodFees()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getShippingFees()).isEqualTo(BigDecimal.valueOf(26));
        assertThat(order.getTransaction()).isNull();
        assertThat(order.getVatFees()).isEqualTo(order.getSubTotalPrice().multiply(BigDecimal.valueOf(0.14)));
        assertThat(customer.getStoreBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.getPaidAmount()).isEqualTo(order.getTotalPrice().subtract(customerBalance));
        assertThat(order.getUsedCustomerBalance()).isEqualTo(customerBalance);
        assertThat(order.isUseCustomerBalance()).isEqualTo(true);
        assertThat(order.getTotalPrice()).isEqualTo(
                order.getSubTotalPrice()
                        .add(order.getCodFees())
                        .add(order.getVatFees())
                        .add(order.getShippingFees()));
    }

    @Test
    public void checkoutPrepaidShouldPass() throws Exception {
        Long customerId = 1l;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setStoreBalance(BigDecimal.valueOf(10));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken("123456");
        request.setPaymentMethod(PaymentMethod.STRIPE);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        Cart cart = new Cart();
        cart.setId(1l);
        cart.setSubTotal(BigDecimal.valueOf(1000));
        cart.setCustomer(customer);

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paid(true)
                .refunded(false)
                .currency("egp")
                .status("success")
                .requestId("12358749")
                .build();

        given(cartService.getCart(customerId)).willReturn(cart);
        given(orderRepository.save(any(Order.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(paymentServiceFactory.getPaymentService(request.getPaymentMethod())).willReturn(paymentService);
        given(paymentService.pay(any())).willReturn(paymentResponse);

        Order order = orderService.checkout(customerId, request);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().size()).isEqualTo(cart.getCartItems().size());
        assertThat(order.getCodFees()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.getShippingFees()).isEqualTo(BigDecimal.valueOf(26));
        assertThat(order.getTransaction()).isNotNull();
        assertThat(order.getVatFees()).isEqualTo(order.getSubTotalPrice().multiply(BigDecimal.valueOf(0.14)));
        assertThat(customer.getStoreBalance()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(order.getPaidAmount()).isEqualTo(order.getTotalPrice());
        assertThat(order.getUsedCustomerBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.isUseCustomerBalance()).isEqualTo(false);
        assertThat(order.getTotalPrice()).isEqualTo(
                order.getSubTotalPrice()
                        .add(order.getCodFees())
                        .add(order.getVatFees())
                        .add(order.getShippingFees()));
    }

    @Test
    public void checkoutPrepaidUsingCustomerBalanceShouldPass() throws Exception {
        Long customerId = 1l;

        BigDecimal customerBalance = BigDecimal.valueOf(10).setScale(2);

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setStoreBalance(customerBalance);

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken("123456");
        request.setPaymentMethod(PaymentMethod.STRIPE);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(true);

        Cart cart = new Cart();
        cart.setId(1l);
        cart.setSubTotal(BigDecimal.valueOf(1000));
        cart.setCustomer(customer);

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paid(true)
                .refunded(false)
                .currency("egp")
                .status("success")
                .requestId("12358749")
                .build();

        given(cartService.getCart(customerId)).willReturn(cart);
        given(orderRepository.save(any(Order.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        given(paymentServiceFactory.getPaymentService(request.getPaymentMethod())).willReturn(paymentService);
        given(paymentService.pay(any())).willReturn(paymentResponse);

        Order order = orderService.checkout(customerId, request);

        assertThat(order).isNotNull();
        assertThat(order.getOrderItems().size()).isEqualTo(cart.getCartItems().size());
        assertThat(order.getCodFees()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.getShippingFees()).isEqualTo(BigDecimal.valueOf(26));
        assertThat(order.getTransaction()).isNotNull();
        assertThat(order.getVatFees()).isEqualTo(order.getSubTotalPrice().multiply(BigDecimal.valueOf(0.14)));
        assertThat(customer.getStoreBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(order.getPaidAmount()).isEqualTo(order.getTotalPrice().subtract(customerBalance));
        assertThat(order.getUsedCustomerBalance()).isEqualTo(customerBalance);
        assertThat(order.isUseCustomerBalance()).isEqualTo(true);
        assertThat(order.getTotalPrice()).isEqualTo(
                order.getSubTotalPrice()
                        .add(order.getCodFees())
                        .add(order.getVatFees())
                        .add(order.getShippingFees()));
    }

    @Test
    public void checkoutNonExistCartShouldThrows() {
        Long customerId = 1l;

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willThrow(new CartNotFoundException(""));

        assertThrows(CartNotFoundException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void checkoutEmptyCartShouldThrows() {
        Long customerId = 1l;

        Cart cart = new Cart();
        cart.setId(1l);

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willReturn(cart);

        assertThrows(CartEmptyException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void checkoutMaximumCartItemsCountExceededShouldThrows() {
        Long customerId = 1l;

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(20);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(10));

        Product product2 = new Product();
        product2.setId(1l);
        product2.setQuantity(20);
        product2.setAvailable(true);
        product2.setPrice(BigDecimal.valueOf(50));

        Product product3 = new Product();
        product3.setId(1l);
        product3.setQuantity(20);
        product3.setAvailable(true);
        product3.setPrice(BigDecimal.valueOf(100));

        Product product4 = new Product();
        product4.setId(1l);
        product4.setQuantity(20);
        product4.setAvailable(true);
        product4.setPrice(BigDecimal.valueOf(100));

        Cart cart = new Cart();
        cart.setId(1l);


        CartItem cartItem = new CartItem(cart, product, 1);
        CartItem cartItem2 = new CartItem(cart, product2, 2);
        CartItem cartItem3 = new CartItem(cart, product3, 3);
        CartItem cartItem4 = new CartItem(cart, product4, 4);

        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem2);
        cart.addCartItem(cartItem3);
        cart.addCartItem(cartItem4);

        cart.setSubTotal(cart.getCartItems()
                .stream()
                .map(cartItem1 -> cartItem1.getUnitPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willReturn(cart);

        assertThrows(CartItemsCountExceededException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void checkoutCartWithNotAvailableItemShouldThrows() {
        Long customerId = 1l;

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(20);
        product.setAvailable(false);
        product.setPrice(BigDecimal.valueOf(10));

        Product product2 = new Product();
        product2.setId(1l);
        product2.setQuantity(20);
        product2.setAvailable(true);
        product2.setPrice(BigDecimal.valueOf(50));

        Product product3 = new Product();
        product3.setId(1l);
        product3.setQuantity(20);
        product3.setAvailable(true);
        product3.setPrice(BigDecimal.valueOf(100));

        Product product4 = new Product();
        product4.setId(1l);
        product4.setQuantity(20);
        product4.setAvailable(true);
        product4.setPrice(BigDecimal.valueOf(100));

        Cart cart = new Cart();
        cart.setId(1l);


        CartItem cartItem = new CartItem(cart, product, 1);
        CartItem cartItem2 = new CartItem(cart, product2, 2);
        CartItem cartItem3 = new CartItem(cart, product3, 3);
        CartItem cartItem4 = new CartItem(cart, product4, 4);

        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem2);
        cart.addCartItem(cartItem3);
        cart.addCartItem(cartItem4);

        cart.setSubTotal(cart.getCartItems()
                .stream()
                .map(cartItem1 -> cartItem1.getUnitPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willReturn(cart);

        assertThrows(ProductNotAvailableException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void checkoutCartItemsAmountExceededShouldThrows() {
        Long customerId = 1l;

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(20);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(10));

        Product product2 = new Product();
        product2.setId(1l);
        product2.setQuantity(20);
        product2.setAvailable(true);
        product2.setPrice(BigDecimal.valueOf(50));

        Product product3 = new Product();
        product3.setId(1l);
        product3.setQuantity(20);
        product3.setAvailable(true);
        product3.setPrice(BigDecimal.valueOf(500));

        Cart cart = new Cart();
        cart.setId(1l);


        CartItem cartItem = new CartItem(cart, product, 5);
        CartItem cartItem2 = new CartItem(cart, product2, 2);
        CartItem cartItem3 = new CartItem(cart, product3, 7);


        cart.addCartItem(cartItem);
        cart.addCartItem(cartItem2);
        cart.addCartItem(cartItem3);


        cart.setSubTotal(cart.getCartItems()
                .stream()
                .map(cartItem1 -> cartItem1.getUnitPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willReturn(cart);

        assertThrows(CartItemsAmountExceededException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void checkoutCartItemsAmountLessThanMinimumShouldThrows() {
        Long customerId = 1l;

        Product product = new Product();
        product.setId(1l);
        product.setQuantity(20);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(10));


        Cart cart = new Cart();
        cart.setId(1l);

        CartItem cartItem = new CartItem(cart, product, 5);


        cart.addCartItem(cartItem);

        cart.setSubTotal(cart.getCartItems()
                .stream()
                .map(cartItem1 -> cartItem1.getUnitPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        CheckoutRequest request = new CheckoutRequest();
        request.setCardToken(null);
        request.setPaymentMethod(PaymentMethod.COD);
        request.setShippingAddressId(1l);
        request.setShippingMethod(ShippingMethod.DOOR_DELIVERY);
        request.setUseCustomerBalance(false);

        given(cartService.getCart(customerId)).willReturn(cart);

        assertThrows(CartItemsAmountLessThanMinimumException.class, () -> orderService.checkout(customerId, request));

        verify(orderRepository, never()).save(any(Order.class));
    }
}
