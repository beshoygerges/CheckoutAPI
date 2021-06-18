package com.menus.checkout.service;

import com.menus.checkout.dto.AddCartItemRequest;
import com.menus.checkout.model.Cart;

public interface CartService {

    Cart addCartItem(AddCartItemRequest request, Long customerId);

    Cart getCart(Long customerId);

    void removeCartItems(Cart cart);
}
