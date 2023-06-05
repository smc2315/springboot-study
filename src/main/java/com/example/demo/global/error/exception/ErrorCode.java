package com.example.demo.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED("C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND("C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR("C004", "Server Error"),
    INVALID_TYPE_VALUE("C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED("C006", "Access is Denied")
    ;

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
