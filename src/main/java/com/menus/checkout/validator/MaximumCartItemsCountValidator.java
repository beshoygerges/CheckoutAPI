package com.menus.checkout.validator;

import com.menus.checkout.exception.CartItemsCountExceededException;
import com.menus.checkout.model.Cart;

import java.util.Objects;

public class MaximumCartItemsCountValidator extends Validator<Cart> {

    @Override
    public void validate(Cart cart) {

        if (cart.getCartItems().size() > 3) {
            throw new CartItemsCountExceededException("Maximum cart items is 3");
        }

        if (Objects.nonNull(getNextValidator())) {
            getNextValidator().validate(cart);
        }

    }
}
