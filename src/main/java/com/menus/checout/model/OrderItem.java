package com.menus.checout.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class OrderItem extends BaseEntity {

    @EmbeddedId
    private OrderItemPk pk;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    public OrderItem(Order order, Product product, Integer quantity) {
        this.pk = new OrderItemPk(order, product);
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Transient
    public Product getProduct() {
        return pk.getProduct();
    }

}
