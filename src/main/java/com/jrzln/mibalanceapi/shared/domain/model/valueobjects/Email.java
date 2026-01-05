package com.jrzln.mibalanceapi.shared.domain.model.valueobjects;

import com.jrzln.mibalanceapi.shared.domain.model.exceptions.InvalidEmailFormatException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Value object for validating email addresses.
 *
 * @param email the email address
 */
public record Email(
        @NotBlank(message = "Email must not be blank")
        @Size(min = 1, max = 255)
        String email
) {

    public Email {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new InvalidEmailFormatException();
        }
    }
}