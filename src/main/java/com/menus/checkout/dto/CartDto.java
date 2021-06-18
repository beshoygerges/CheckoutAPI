package com.menus.checkout.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto implements Serializable {

    private Long id;
    private Integer itemsCount;
    private Integer totalQuantity;
    private BigDecimal subTotal;
    private List<CartItemDto> cartItems;


}
