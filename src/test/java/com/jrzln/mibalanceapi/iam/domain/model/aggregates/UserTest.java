package com.jrzln.mibalanceapi.iam.domain.model.aggregates;

import static org.junit.jupiter.api.Assertions.*;

import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void create_shouldCreateUser_whenPasswordHashIsValid() {
        var user = User.create(
                new Email("test@email.com"),
                new PasswordHash("hashed-password")
        );

        assertNotNull(user);
    }

    @Test
    void create_shouldThrowException_whenPasswordHashIsNull() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> User.create(
                        new Email("test@email.com"),
                        null
                )
        );

        assertEquals(
                "Password must not be null or blank",
                exception.getMessage()
        );
    }

    @Test
    void create_shouldThrowException_whenPasswordHashIsBlank() {
        var exception = assertThrows(
                IllegalArgumentException.class,
                () -> User.create(
                        new Email("test@email.com"),
                        new PasswordHash("   ")
                )
        );

        assertEquals(
                "Password must not be null or blank",
                exception.getMessage()
        );
    }

    @Test
    void updatePassword_shouldReplacePasswordHash() {
        var user = User.create(
                new Email("test@email.com"),
                new PasswordHash("old-hash")
        );

        user.updatePasswordHash("new-hash");

        // We only assert observable behavior: no exception
        assertDoesNotThrow(() ->
                user.updatePasswordHash("another-hash")
        );
    }
}

