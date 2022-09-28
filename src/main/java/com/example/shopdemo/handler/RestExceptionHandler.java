package com.example.shopdemo.handler;

import com.example.shopdemo.exception.UnauthenticatedException;
import com.example.shopdemo.pojo.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResponse unauthenticated(UnauthenticatedException e) {
        return RestResponse
                .build()
                .status(HttpStatus.UNAUTHORIZED)
                .messages(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse constraintViolations(ConstraintViolationException e) {
        e.printStackTrace();
        return RestResponse
                .build()
                .status(HttpStatus.BAD_REQUEST)
                .messages(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse globalHandler(Exception e) {
        e.printStackTrace();
        return RestResponse
                .build()
                .status(HttpStatus.BAD_REQUEST)
                .messages(e.getMessage());
    }
}
