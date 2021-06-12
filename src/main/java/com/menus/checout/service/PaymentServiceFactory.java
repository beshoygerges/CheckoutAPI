package com.menus.checout.service;

import com.menus.checout.constants.PaymentMethod;

public interface PaymentServiceFactory {

    PaymentService getPaymentService(PaymentMethod paymentMethod);

}
