package com.menus.checout.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
