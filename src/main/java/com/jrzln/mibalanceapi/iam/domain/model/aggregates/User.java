package com.jrzln.mibalanceapi.iam.domain.model.aggregates;

import com.jrzln.mibalanceapi.iam.domain.model.exceptions.InvalidPasswordException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.InvalidPasswordHashException;
import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.shared.domain.model.aggregates.AuditableDocument;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * User aggregate root representing a system user.
 */
@Getter
@Document(collection = "users")
public class User extends AuditableDocument {

    // Username represented as an Email value object
    @Valid
    @Field("username")
    @Indexed(unique = true)
    private Email username;

    // Password field with validation
    @Field("password")
    private PasswordHash passwordHash;

    // Protected no-argument constructor for framework use
    protected User() {}

    // Constructor to initialize User with username and password
    private User(Email username, PasswordHash passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Static factory method to create a new User instance.
     *
     * @param username the user's email as username
     * @param password the user's password
     *
     * @return a new User instance
     */
    public static User create(Email username, PasswordHash password) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null");
        }

        if (password == null) {
            throw new InvalidPasswordHashException("Password must not be null or blank");
        }

        return new User(username, password);
    }

    /**
     * Updates the user's password.
     *
     * @param newPasswordHash the new password to set
     */
    public void updatePasswordHash(String newPasswordHash) {
        this.passwordHash.update(newPasswordHash) ;
    }
}