package com.menus.checkout.validator;

import com.menus.checkout.exception.CartEmptyException;
import com.menus.checkout.model.Cart;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CartEmptyValidator extends Validator<Cart> {

    @Override
    public void validate(Cart cart) {

        Map<String, String> result = new LinkedHashMap<>();

        boolean isEmpty = cart.getCartItems().isEmpty();

        if (isEmpty) {
            throw new CartEmptyException("cart is empty");
        }

        if (Objects.nonNull(getNextValidator())) {
            getNextValidator().validate(cart);
        }

    }
}
