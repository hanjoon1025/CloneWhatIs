package com.team10.whatis.global.exception;

import com.team10.whatis.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class,MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDto> handleCustomException(Exception e) {
        ResponseDto<Object> objectResponseDto = ResponseDto.setBadRequest(e.getMessage());
        return new ResponseEntity<>(objectResponseDto, HttpStatus.BAD_REQUEST);
    }
}