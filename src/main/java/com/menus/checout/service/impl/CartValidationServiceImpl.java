package com.menus.checout.service.impl;

import com.menus.checout.model.Cart;
import com.menus.checout.service.CartValidationService;
import com.menus.checout.service.Validator;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CartValidationServiceImpl implements CartValidationService {

    @Override
    public void validateCart(Cart cart) {
        Validator<Cart> cartEmptyValidator = new CartEmptyValidator();
        Validator<Cart> itemsAvailabilityValidator = new ItemsAvailabilityValidator();
        Validator<Cart> totalCartAmountValidator = new MinimumCartAmountValidator();
        Validator<Cart> maximumCartAmountValidator = new MaximumCartAmountValidator();

        cartEmptyValidator.setNextValidator(itemsAvailabilityValidator);
        itemsAvailabilityValidator.setNextValidator(totalCartAmountValidator);
        totalCartAmountValidator.setNextValidator(maximumCartAmountValidator);

        Map<String, String> result = cartEmptyValidator.validate(cart);
        if (!CollectionUtils.isEmpty(result)) {
            throw new RuntimeException(result.get("Error"));
        }
    }
}
