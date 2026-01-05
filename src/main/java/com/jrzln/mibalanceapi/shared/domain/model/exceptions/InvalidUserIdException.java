package com.jrzln.mibalanceapi.shared.domain.model.exceptions;

/**
 * Exception thrown when a User ID is invalid.
 */
public class InvalidUserIdException extends RuntimeException {

    public InvalidUserIdException(String message) {
        super(message);
    }
}
