package com.menus.checkout.exception;

public class CartItemsCountExceededException extends RuntimeException {

    public CartItemsCountExceededException(String message) {
        super(message);
    }
}