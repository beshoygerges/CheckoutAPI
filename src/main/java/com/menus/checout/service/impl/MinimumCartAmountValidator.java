package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class MinimumCartAmountValidator extends Validator<Cart> {

    @Override
    public Map<String, String> validate(Cart cart) {

        BigDecimal totalAmount = cart.getSubTotal();

        if (totalAmount.compareTo(BigDecimal.valueOf(100)) < 0) {
            result.put("Error", "Minimum amount for cart items is 100");
            return result;
        }

        if (Objects.nonNull(getNextValidator())) {
            return getNextValidator().validate(cart);
        }
        return result;
    }
}
