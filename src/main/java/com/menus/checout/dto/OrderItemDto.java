package com.menus.checout.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto implements Serializable {

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private ProductDto product;

}
