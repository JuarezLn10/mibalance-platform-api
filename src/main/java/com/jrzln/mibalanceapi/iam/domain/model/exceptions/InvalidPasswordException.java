package com.jrzln.mibalanceapi.iam.domain.model.exceptions;

/**
 * Exception thrown when a provided password is invalid
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super("The given password is invalid: " + message);
    }
}
