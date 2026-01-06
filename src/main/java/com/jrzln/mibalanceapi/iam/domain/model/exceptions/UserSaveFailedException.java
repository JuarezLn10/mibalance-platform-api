package com.jrzln.mibalanceapi.iam.domain.model.exceptions;

public class UserSaveFailedException extends RuntimeException {
    public UserSaveFailedException(String message, Exception ex) {
        super("Could not save the user " + message, ex);
    }
}
