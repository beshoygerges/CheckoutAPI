package com.menus.checkout.repository;

import com.menus.checkout.model.CartItem;
import com.menus.checkout.model.CartItemPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, CartItemPk> {

}
