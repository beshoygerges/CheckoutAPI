package com.menus.checkout.dto;

import com.menus.checkout.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentRequest {

    private String cardToken;
    private Integer transactionAmount;
    private Currency currency;

}
