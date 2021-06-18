package com.menus.checkout.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDto implements Serializable {

    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;

}
