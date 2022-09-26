package com.example.shopdemo.exception;

public class InsufficientException extends RuntimeException {
    public InsufficientException(String message) {
        super(message);
    }
}
