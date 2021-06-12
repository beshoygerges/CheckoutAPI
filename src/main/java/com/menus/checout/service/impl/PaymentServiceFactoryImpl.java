package com.menus.checout.service.impl;

import com.menus.checout.constants.PaymentMethod;
import com.menus.checout.service.PaymentService;
import com.menus.checout.service.PaymentServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceFactoryImpl implements PaymentServiceFactory {

    final PaymentService stripePaymentService;

    @Override
    public PaymentService getPaymentService(PaymentMethod paymentMethod) {
        if (paymentMethod == PaymentMethod.STRIPE) {
            return stripePaymentService;
        }
        return null;
    }
}
