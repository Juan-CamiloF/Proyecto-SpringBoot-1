package com.springboot.backend.proyecto1.exception;

public class UploadFileException extends RuntimeException {
    public UploadFileException(String message) {
        super(message);
    }
}
