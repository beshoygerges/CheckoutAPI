package com.menus.checkout.dto;

import com.menus.checkout.constants.PaymentMethod;
import com.menus.checkout.constants.ShippingMethod;
import com.menus.checkout.constants.Status;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private Long id;
    private Status status;
    private BigDecimal subTotalPrice;
    private BigDecimal shippingFees;
    private BigDecimal codFees;
    private BigDecimal vatFees;
    private boolean useCustomerBalance;
    private BigDecimal usedCustomerBalance;
    private BigDecimal totalPrice;
    private BigDecimal paidAmount;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    private List<OrderItemDto> orderItems;
}
