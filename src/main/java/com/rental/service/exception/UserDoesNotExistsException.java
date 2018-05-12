package com.rental.service.exception;

public class UserDoesNotExistsException extends RuntimeException{

    public UserDoesNotExistsException() {
    }

    public UserDoesNotExistsException(String message) {

        super(message);
    }

    public UserDoesNotExistsException(String message, Throwable cause) {

        super(message, cause);
    }
}
