package com.jrzln.mibalanceapi.profiles.domain.model.exceptions;

/**
 * Exception thrown when a profile update operation fails.
 */
public class ProfileUpdateFailedException extends RuntimeException {
    public ProfileUpdateFailedException(String message) {
        super(message);
    }
}
