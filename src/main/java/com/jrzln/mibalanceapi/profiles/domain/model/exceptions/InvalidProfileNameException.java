package com.jrzln.mibalanceapi.profiles.domain.model.exceptions;

/**
 * Exception thrown when a profile name is invalid.
 */
public class InvalidProfileNameException extends RuntimeException {
    public InvalidProfileNameException(String message) {
        super(message);
    }
}
