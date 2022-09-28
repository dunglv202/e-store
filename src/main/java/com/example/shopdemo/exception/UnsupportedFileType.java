package com.example.shopdemo.exception;

public class UnsupportedFileType extends RuntimeException {
    public UnsupportedFileType(String message) {
        super("Unsupported File Type: " + message);
    }
}
