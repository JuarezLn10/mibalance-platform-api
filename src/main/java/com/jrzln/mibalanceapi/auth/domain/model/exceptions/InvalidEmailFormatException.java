package com.jrzln.mibalanceapi.auth.domain.model.exceptions;

/**
 * Exception thrown when an email format is invalid.
 */
public class InvalidEmailFormatException extends RuntimeException {

    public InvalidEmailFormatException() {
        super("Invalid email format");
    }

    public InvalidEmailFormatException(Throwable cause) {
        super("Invalid email format:", cause);
    }
}
