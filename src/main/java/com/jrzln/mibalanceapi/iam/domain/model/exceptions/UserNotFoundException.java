package com.jrzln.mibalanceapi.iam.domain.model.exceptions;

/**
 * Exception thrown when a user with a specified identifier is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String cause) {
        super("User with the given identifier was not found: " + cause);
    }
}
