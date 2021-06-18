package com.menus.checkout.validator;

import com.menus.checkout.exception.CartItemsAmountExceededException;
import com.menus.checkout.model.Cart;

import java.math.BigDecimal;
import java.util.Objects;

public class MaximumCartAmountValidator extends Validator<Cart> {

    @Override
    public void validate(Cart cart) {

        BigDecimal totalAmount = cart.getSubTotal();

        if (totalAmount.compareTo(BigDecimal.valueOf(1500)) > 0) {
            throw new CartItemsAmountExceededException("Maximum amount for cart items is 1500");
        }

        if (Objects.nonNull(getNextValidator())) {
            getNextValidator().validate(cart);
        }

    }
}
