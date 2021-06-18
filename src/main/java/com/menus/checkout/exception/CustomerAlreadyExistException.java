package com.menus.checkout.exception;

public class CustomerAlreadyExistException extends RuntimeException {

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
