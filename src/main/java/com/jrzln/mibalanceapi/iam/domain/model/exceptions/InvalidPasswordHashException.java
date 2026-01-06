package com.jrzln.mibalanceapi.iam.domain.model.exceptions;

/**
 * Exception thrown when a provided password hash is invalid.
 */
public class InvalidPasswordHashException extends RuntimeException {
    public InvalidPasswordHashException(String message) {
        super(message);
    }
}
