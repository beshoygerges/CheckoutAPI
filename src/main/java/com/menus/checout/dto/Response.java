package com.menus.checout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Response<T extends Serializable> implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private ResponseStatus status = ResponseStatus.SUCCESS_RESPONSE_STATUS;
    private T data;

    public Response(T data) {
        this.data = data;
    }

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public static <T extends Serializable> Response<T> of(T data) {
        return new Response(data);
    }

    public static Response of(String message, Integer httpStatusCode) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setSuccess(false);
        responseStatus.setMessage(message);
        responseStatus.setHttpStatusCode(httpStatusCode);
        return new Response(responseStatus);
    }

}
