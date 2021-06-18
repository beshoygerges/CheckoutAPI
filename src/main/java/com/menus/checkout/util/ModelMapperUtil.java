package com.menus.checkout.util;

import org.modelmapper.ModelMapper;

public final class ModelMapperUtil {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private ModelMapperUtil() {

    }

    public static <T> T map(Object source, Class<T> target) {
        return MODEL_MAPPER.map(source, target);
    }


}
