package com.menus.checkout.exception;

public class InsufficientProductQuantityException extends RuntimeException {

    public InsufficientProductQuantityException(String message) {
        super(message);
    }
}
