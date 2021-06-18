package com.menus.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus implements Serializable {

    public static final ResponseStatus SUCCESS_RESPONSE_STATUS = ResponseStatus.builder()
            .httpStatusCode(200)
            .success(true)
        .message("Request processed successfully")
        .build();


    private Integer httpStatusCode;
    private boolean success;
    private String message;

}
