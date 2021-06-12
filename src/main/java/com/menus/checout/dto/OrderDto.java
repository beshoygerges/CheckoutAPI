package com.menus.checout.dto;

import com.menus.checout.constants.PaymentMethod;
import com.menus.checout.constants.Status;
import com.menus.checout.constants.ShippingMethod;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

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
