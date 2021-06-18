package com.menus.checkout.exception;

public class CartItemsAmountLessThanMinimumException extends RuntimeException {

    public CartItemsAmountLessThanMinimumException(String message) {
        super(message);
    }
}
