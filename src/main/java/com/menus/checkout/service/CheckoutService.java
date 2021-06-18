package com.menus.checkout.service;

import com.menus.checkout.dto.CheckoutRequest;
import com.menus.checkout.model.Order;

public interface CheckoutService {

    Order checkout(Long customerId, CheckoutRequest request) throws Exception;

}
