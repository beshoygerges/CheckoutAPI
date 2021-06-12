package com.menus.checout.service;

import com.menus.checout.dto.PaymentRequest;
import com.menus.checout.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse pay(PaymentRequest request) throws Exception;

}
