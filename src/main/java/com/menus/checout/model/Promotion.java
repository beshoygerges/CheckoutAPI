package com.menus.checout.model;

import com.menus.checout.constants.DiscountType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

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
