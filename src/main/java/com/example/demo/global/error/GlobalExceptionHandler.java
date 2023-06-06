package com.example.demo.global.error;

import com.example.demo.global.error.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeException() {

        return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}