package com.petpal.petpalservice.exception;

/**
 * ResourceNotFoundException
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){

    }   
    public ResourceNotFoundException(String message){
        super(message);
    }
}

