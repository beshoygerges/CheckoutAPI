package com.menus.checkout.service.impl;

import com.menus.checkout.model.Cart;
import com.menus.checkout.service.CartValidationService;
import com.menus.checkout.validator.*;

public class CartValidationServiceImpl implements CartValidationService {

    @Override
    public void validateCart(Cart cart) {
        Validator<Cart> cartEmptyValidator = new CartEmptyValidator();
        Validator<Cart> itemsAvailabilityValidator = new ItemsAvailabilityValidator();
        Validator<Cart> totalCartAmountValidator = new MinimumCartAmountValidator();
        Validator<Cart> maximumCartAmountValidator = new MaximumCartAmountValidator();
        Validator<Cart> maximumCartItemsCountValidator = new MaximumCartItemsCountValidator();

        cartEmptyValidator.setNextValidator(itemsAvailabilityValidator);
        itemsAvailabilityValidator.setNextValidator(totalCartAmountValidator);
        totalCartAmountValidator.setNextValidator(maximumCartAmountValidator);
        maximumCartAmountValidator.setNextValidator(maximumCartItemsCountValidator);

        cartEmptyValidator.validate(cart);
    }
}
