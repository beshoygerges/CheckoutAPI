package com.menus.checkout.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemDto implements Serializable {

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private ProductDto product;

}
