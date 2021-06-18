package com.menus.checkout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class CartPromotion implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private CartPromotionPk pk;

    public CartPromotion(Cart cart, Promotion promotion) {
        this.pk = new CartPromotionPk(cart, promotion);
    }

}
