package com.menus.checkout.validator;

import com.menus.checkout.exception.ProductNotAvailableException;
import com.menus.checkout.model.Cart;

import java.util.Objects;

public class ItemsAvailabilityValidator extends Validator<Cart> {

    @Override
    public void validate(Cart cart) {

        boolean isError = cart.getCartItems().stream()
                .anyMatch(item -> !item.getProduct().isAvailable());

        if (isError) {
            throw new ProductNotAvailableException("Product(s) is not available");
        }

        if (Objects.nonNull(getNextValidator())) {
            getNextValidator().validate(cart);
        }

    }
}
