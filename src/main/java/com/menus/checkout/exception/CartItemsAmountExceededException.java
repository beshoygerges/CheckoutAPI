package com.menus.checkout.exception;

public class CartItemsAmountExceededException extends RuntimeException {

    public CartItemsAmountExceededException(String message) {
        super(message);
    }
}
