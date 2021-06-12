package com.menus.checout.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CartDto implements Serializable {

    private Long id;
    private Integer itemsCount;
    private Integer totalQuantity;
    private BigDecimal subTotal;
    private List<CartItemDto> cartItems;


}
