package com.menus.checout.service;

import com.menus.checout.dto.CheckoutRequest;
import com.menus.checout.dto.OrderDto;

public interface OrderService {

    OrderDto addOrder(Long customerId, CheckoutRequest request) throws Exception;

}
