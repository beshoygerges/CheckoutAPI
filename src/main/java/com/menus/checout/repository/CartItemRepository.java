package com.menus.checout.repository;

import com.menus.checout.model.CartItem;
import com.menus.checout.model.CartItemPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, CartItemPk> {

}
