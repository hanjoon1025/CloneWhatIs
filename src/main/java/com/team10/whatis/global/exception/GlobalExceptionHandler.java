package com.team10.whatis.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> vaildError(MethodArgumentNotValidException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}