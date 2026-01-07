package com.jrzln.mibalanceapi.profiles.domain.model.exceptions;

/**
 * Exception thrown when saving a profile fails.
 */
public class ProfileSaveFailedException extends RuntimeException {
    public ProfileSaveFailedException(String message) {
        super(message);
    }
}
