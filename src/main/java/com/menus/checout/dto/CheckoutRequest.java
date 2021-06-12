package com.menus.checout.dto;

import com.menus.checout.constants.PaymentMethod;
import com.menus.checout.constants.ShippingMethod;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest implements Serializable {

    @NotNull
    @NotEmpty
    @NotBlank
    private String cardToken;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
    @NotNull
    private Long shippingAddressId;
    private boolean useCustomerBalance;


}
