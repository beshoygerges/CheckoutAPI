package com.menus.checkout.dto;

import com.menus.checkout.constants.PaymentMethod;
import com.menus.checkout.constants.ShippingMethod;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CheckoutRequest implements Serializable {

    private String cardToken;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    @NotNull
    private Long shippingAddressId;
    private boolean useCustomerBalance;


}
