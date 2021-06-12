package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.Validator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ItemsAvailabilityValidator extends Validator<Cart> {

    @Override
    public Map<String, String> validate(Cart cart) {

        Map<String, String> result = new LinkedHashMap<>();

        boolean isError = cart.getCartItems().stream()
            .anyMatch(item -> !item.getProduct().isAvailable());

        if (isError) {
            result.put("Error", "Product(s) is not available");
            return result;
        }

        if (Objects.nonNull(getNextValidator())) {
            return getNextValidator().validate(cart);
        }
        return result;
    }
}
