package com.jrzln.mibalanceapi.auth.domain.model.exceptions;

import org.springframework.dao.DataAccessException;

public class UserSaveFailedException extends RuntimeException {
    public UserSaveFailedException(String message, DataAccessException ex) {
        super("Could not save the user " + message, ex);
    }
}
