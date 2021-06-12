package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class MaximumCartAmountValidator extends Validator<Cart> {

    @Override
    public Map<String, String> validate(Cart cart) {

        BigDecimal totalAmount = cart.getSubTotal();

        if (totalAmount.compareTo(BigDecimal.valueOf(1500)) > 0) {
            result.put("Error", "Maximum amount for cart items is 1500");
            return result;
        }

        if (Objects.nonNull(getNextValidator())) {
            return getNextValidator().validate(cart);
        }
        return result;
    }
}
