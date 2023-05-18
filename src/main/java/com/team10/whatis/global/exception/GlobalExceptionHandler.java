package com.team10.whatis.global.exception;

import com.team10.whatis.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ResponseDto> handleCustomException(Exception e) {
        ResponseDto<Object> objectResponseDto = ResponseDto.setBadRequest(e.getMessage());
        return new ResponseEntity<>(objectResponseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Valid 예외 처리
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity signValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ResponseDto responseDto = ResponseDto.setBadRequest("valid error", errorMap);
        return ResponseEntity.badRequest().body(responseDto);
    }
}