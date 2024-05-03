package com.petpal.petpalservice.exception;

public class ResourceDuplicateException extends RuntimeException{
    public ResourceDuplicateException() {
    }

    public ResourceDuplicateException(String message) {
        super(message);
    }
}
