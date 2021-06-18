package com.menus.checkout.model;

import com.menus.checkout.constants.DiscountType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long productId;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    private Long quantity;

    private Double discountFactor;

}
