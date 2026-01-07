package com.jrzln.mibalanceapi.profiles.domain.model.exceptions;

/**
 * Exception thrown when an invalid region is provided for a profile.
 */
public class InvalidRegionException extends RuntimeException {
    public InvalidRegionException(String message) {
        super(message);
    }
}
