package com.jrzln.mibalanceapi.iam.domain.model.exceptions;

/**
 * Exception thrown when a username already exists in the system.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String cause) {
        super("Username already exists: " + cause);
    }
}
