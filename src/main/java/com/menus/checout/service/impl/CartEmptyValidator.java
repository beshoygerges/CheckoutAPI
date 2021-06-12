package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.Validator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CartEmptyValidator extends Validator<Cart> {

    @Override
    public Map<String, String> validate(Cart cart) {

        Map<String, String> result = new LinkedHashMap<>();

        boolean isEmpty = cart.getCartItems().isEmpty();

        if (isEmpty) {
            result.put("Error", "Cart is Empty");
            return result;
        }

        if (Objects.nonNull(getNextValidator())) {
            return getNextValidator().validate(cart);
        }
        return result;
    }
}
