package com.example.identityservice.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    UNAUTHENTICATED(1003, "Unauthenticated"),

    USER_NOT_EXISTED(1002, "User not existed");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

}
