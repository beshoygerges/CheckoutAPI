package com.menus.checout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse {

    private String status;
    private boolean refunded;
    private boolean paid;
    private String currency;
    private String requestId;

}
