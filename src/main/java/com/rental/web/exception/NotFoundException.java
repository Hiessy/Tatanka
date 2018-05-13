package com.rental.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>NotFoundException is thrown when the requested resource has not been found.
 * Following the HTTP/1.1 standard with the NOT_FOUND status code</p>
 *
 * @author Martín
 * @version 1.0
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
