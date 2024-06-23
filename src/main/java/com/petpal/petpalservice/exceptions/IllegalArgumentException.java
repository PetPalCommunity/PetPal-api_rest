package com.petpal.petpalservice.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException() {
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
