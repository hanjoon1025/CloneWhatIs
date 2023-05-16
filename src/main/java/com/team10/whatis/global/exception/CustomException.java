package com.team10.whatis.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }


}