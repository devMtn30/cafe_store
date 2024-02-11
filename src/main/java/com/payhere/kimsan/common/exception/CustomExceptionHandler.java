package com.payhere.kimsan.common.exception;

import com.payhere.kimsan.common.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CustomResponse<Object>> handleCustomException(CustomException e) {
        CustomResponse<Object> response = new CustomResponse<>();
        response.setMeta(e.getErrorCode().getHttpStatus(), e.getMessage());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getHttpStatus().value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomResponse<Object>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        String field = e.getBindingResult().getFieldError().getField();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        CustomResponse<Object> response = new CustomResponse<>();
        response.setMeta(HttpStatus.BAD_REQUEST, field + "는 " + message);
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
