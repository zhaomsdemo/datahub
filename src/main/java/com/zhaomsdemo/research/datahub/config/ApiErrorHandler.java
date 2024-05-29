package com.zhaomsdemo.research.datahub.config;

import com.zhaomsdemo.research.datahub.exception.UserNotFoundException;
import com.zhaomsdemo.research.datahub.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()))
                .body(ErrorMessage.builder()
                        .message(e.getMessage())
                        .errorCode("404-001")
                        .build());
    }
}
