package com.menus.checkout.service;

import com.menus.checkout.dto.PaymentRequest;
import com.menus.checkout.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse pay(PaymentRequest request) throws Exception;

}
