package com.menus.checkout.exception;

public class CartEmptyException extends RuntimeException {

    public CartEmptyException(String message) {
        super(message);
    }
}