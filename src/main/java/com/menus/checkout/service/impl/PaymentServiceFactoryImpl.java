package com.menus.checkout.service.impl;

import com.menus.checkout.constants.PaymentMethod;
import com.menus.checkout.service.PaymentService;
import com.menus.checkout.service.PaymentServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceFactoryImpl implements PaymentServiceFactory {

    private final PaymentService stripePaymentService;

    @Override
    public PaymentService getPaymentService(PaymentMethod paymentMethod) {
        if (paymentMethod == PaymentMethod.STRIPE) {
            return stripePaymentService;
        }
        return null;
    }
}
