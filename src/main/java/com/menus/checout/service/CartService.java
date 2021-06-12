package com.menus.checout.service;

import com.menus.checout.dto.AddCartItemRequest;
import com.menus.checout.dto.CartDto;
import com.menus.checout.model.Cart;

public interface CartService {

    CartDto addCartItem(AddCartItemRequest request, Long customerId);

    Cart getCart(Long customerId);

    void removeCartItems(Cart cart);
}
