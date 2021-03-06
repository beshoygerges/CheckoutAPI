package com.menus.checkout.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subTotal = BigDecimal.ZERO;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "customer_id")
    @OneToOne(optional = false)
    private Customer customer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.cart", orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.cart", orphanRemoval = true)
    private List<CartPromotion> cartPromotions = new ArrayList<>();

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    @Transient
    public Integer getItemsCount() {
        return cartItems.size();
    }

    @Transient
    public Integer getTotalQuantity() {
        return cartItems.stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
    }

}
