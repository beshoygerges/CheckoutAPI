package com.menus.checkout.service.impl;

import com.menus.checkout.dto.PaymentRequest;
import com.menus.checkout.dto.PaymentResponse;
import com.menus.checkout.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service("Stripe")
public class StripePaymentService implements PaymentService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentResponse pay(PaymentRequest request) throws Exception {
        PaymentResponse paymentResponse = null;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getTransactionAmount());
        chargeParams.put("currency", request.getCurrency());
        chargeParams.put("source", request.getCardToken());
        Charge charge = Charge.create(chargeParams);
        paymentResponse = PaymentResponse.builder()
            .paid(charge.getPaid())
            .refunded(charge.getRefunded())
            .currency(charge.getCurrency())
            .status(charge.getStatus())
            .requestId(charge.getId())
            .build();

        return paymentResponse;
    }

}
