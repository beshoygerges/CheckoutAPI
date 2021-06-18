package com.menus.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    private String pictureUrl;
    @Column(nullable = false)
    private Integer quantity;
    private boolean available;

    public void decreaseQuantity(Integer quantity) {
        this.quantity -= quantity;
    }
}
