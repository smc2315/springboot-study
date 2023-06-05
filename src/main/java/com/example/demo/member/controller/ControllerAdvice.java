package com.example.demo.member.controller;

import com.example.demo.member.controller.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MemberResponse> runtimeException() {
        return ResponseEntity.badRequest().body(MemberResponse.builder().message("fail").build());
    }
}