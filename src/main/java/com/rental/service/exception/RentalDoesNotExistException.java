package com.rental.service.exception;

public class RentalDoesNotExistException extends RuntimeException {

    public RentalDoesNotExistException() {
    }

    public RentalDoesNotExistException(String message) {

        super(message);
    }

    public RentalDoesNotExistException(String message, Throwable cause) {

        super(message, cause);
    }
}
