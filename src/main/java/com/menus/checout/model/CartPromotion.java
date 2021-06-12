package com.menus.checout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

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
