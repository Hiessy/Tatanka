package com.rental.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>ConflictException caused when a resource is trying to create an object with
 *    an existing attribute that is unique.</p>
 *
 * @author Martín
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
