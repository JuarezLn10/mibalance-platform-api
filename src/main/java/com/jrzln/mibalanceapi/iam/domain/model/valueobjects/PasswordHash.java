package com.jrzln.mibalanceapi.iam.domain.model.valueobjects;

import com.jrzln.mibalanceapi.iam.domain.model.exceptions.InvalidPasswordHashException;

/**
 * Value object representing a password hash.
 *
 * @param value the password hash string
 */
public record PasswordHash(String value) {

    // Constructor to validate the password hash
    public PasswordHash {
        if (value == null || value.isBlank()) {
            throw new InvalidPasswordHashException("Password hash cannot be null or blank");
        }
    }

    /**
     * Updates the password hash with a new value.
     *
     * @param newValue the new password hash value
     * @return a new PasswordHash instance with the updated value
     */
    public PasswordHash update(String newValue) {
        return new PasswordHash(newValue);
    }
}
