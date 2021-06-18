package com.menus.checkout.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddCartItemRequest implements Serializable {

    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private Integer quantity;

}
