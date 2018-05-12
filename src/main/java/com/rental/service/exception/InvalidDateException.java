package com.rental.service.exception;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException() {
    }

    public InvalidDateException(String message) {

        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {

        super(message, cause);
    }
}
