package com.E_Commerce.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){

        super(message);
    }
}

