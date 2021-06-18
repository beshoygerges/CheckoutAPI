package com.menus.checkout.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class CartItem extends BaseEntity {

    @EmbeddedId
    private CartItemPk pk;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    public CartItem(Cart cart, Product product, Integer quantity) {
        this.pk = new CartItemPk(cart, product);
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Transient
    public Cart getCart() {
        return this.pk.getCart();
    }

    @Transient
    public Long getCartId() {
        return this.pk.getCart().getId();
    }

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

}
