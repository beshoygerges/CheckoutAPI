package com.menus.checout.dto;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemRequest implements Serializable {

    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private Integer quantity;

}
