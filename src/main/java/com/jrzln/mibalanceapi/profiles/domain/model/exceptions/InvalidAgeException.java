package com.jrzln.mibalanceapi.profiles.domain.model.exceptions;

/**
 * Exception thrown when an invalid age is provided for a profile.
 */
public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}
