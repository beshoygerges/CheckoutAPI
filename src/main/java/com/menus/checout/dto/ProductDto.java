package com.menus.checout.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDto implements Serializable {

    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;

}
