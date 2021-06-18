package com.menus.checkout.validator;

import lombok.Data;

@Data
public abstract class Validator<T> {

    protected Validator<T> nextValidator;

    public abstract void validate(T t);

}
