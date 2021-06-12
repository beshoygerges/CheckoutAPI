package com.menus.checout.service;

import com.menus.checout.dto.PaymentRequest;
import com.menus.checout.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
