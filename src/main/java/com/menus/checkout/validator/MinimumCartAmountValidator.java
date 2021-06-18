package com.menus.checkout.validator;

import com.menus.checkout.exception.CartItemsAmountLessThanMinimumException;
import com.menus.checkout.model.Cart;

import java.math.BigDecimal;
import java.util.Objects;

public class MinimumCartAmountValidator extends Validator<Cart> {

    @Override
    public void validate(Cart cart) {

        BigDecimal totalAmount = cart.getSubTotal();

        if (totalAmount.compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new CartItemsAmountLessThanMinimumException("Minimum amount for cart items is 100");
        }

        if (Objects.nonNull(getNextValidator())) {
            getNextValidator().validate(cart);
        }

    }
}
