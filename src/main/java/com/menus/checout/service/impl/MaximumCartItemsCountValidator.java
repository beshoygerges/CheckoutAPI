package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.Validator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class MaximumCartItemsCountValidator extends Validator<Cart> {

    @Override
    public Map<String, String> validate(Cart cart) {

        Map<String, String> result = new LinkedHashMap<>();

        if (Objects.nonNull(getNextValidator())) {
            return getNextValidator().validate(cart);
        }
        return result;
    }
}
