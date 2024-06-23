package com.petpal.petpalservice.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
    }
    public InternalServerException(String message) {
        super(message);
    }
}