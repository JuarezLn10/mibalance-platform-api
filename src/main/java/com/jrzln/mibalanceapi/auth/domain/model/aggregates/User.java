package com.jrzln.mibalanceapi.auth.domain.model.aggregates;

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
    @NotBlank(message = "Password must not be blank")
    private String password;

    // Protected no-argument constructor for framework use
    protected User() {}

    // Constructor to initialize User with username and password
    public User(Email username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Updates the user's password.
     * @param newPassword the new password to set
     */
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}