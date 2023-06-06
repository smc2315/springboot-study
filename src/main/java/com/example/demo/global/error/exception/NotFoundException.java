package com.example.demo.global.error.exception;

public class NotFoundException extends CommonException{

    public NotFoundException(String message) {
        super(message,ErrorCode.ENTITY_NOT_FOUND);
    }
}
