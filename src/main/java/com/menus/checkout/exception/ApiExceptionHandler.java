package com.menus.checkout.exception;

import com.menus.checkout.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public final Response handleUpdateFailException(Exception exception) {
        log.error(exception.getMessage());
        return Response.of(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

}
