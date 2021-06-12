package com.menus.checout.service;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;

@Data
public abstract class Validator<T> {

    protected Validator<T> nextValidator;
    protected Map<String, String> result = new LinkedHashMap<>();

    public abstract Map<String, String> validate(T t);

}
