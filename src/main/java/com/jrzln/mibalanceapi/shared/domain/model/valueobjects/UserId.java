package com.jrzln.mibalanceapi.shared.domain.model.valueobjects;

import com.jrzln.mibalanceapi.shared.domain.model.exceptions.InvalidUserIdException;
import jakarta.validation.constraints.NotBlank;

/**
 * Value object representing a User ID.
 *
 * @param userId the unique identifier for a user
 */
public record UserId(
        @NotBlank(message = "UserId must not be blank")
        String userId
) {

    /**
     * Validates the UserId upon creation.
     *
     * @param userId the unique identifier for a user
     *
     */
    public UserId {
        if (userId == null || userId.isBlank()) {
            throw new InvalidUserIdException("UserId must not be null or blank");
        }
    }
}
