package com.petpal.petpalservice.exception;

/**
 * BadRequestException
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(){

    }   
    public BadRequestException(String message){
        super(message);
    }
}
