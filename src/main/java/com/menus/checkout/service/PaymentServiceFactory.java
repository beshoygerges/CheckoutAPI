package com.menus.checkout.service;

import com.menus.checkout.constants.PaymentMethod;

public interface PaymentServiceFactory {

    PaymentService getPaymentService(PaymentMethod paymentMethod);

}
