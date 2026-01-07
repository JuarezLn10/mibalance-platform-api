package com.jrzln.mibalanceapi.profiles.domain.model.aggregates;

import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidAgeException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidProfileNameException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidRegionException;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProfileTest {

    @Test
    void create_shouldCreateProfileSuccessfully() {

        // Arrange
        var name = "John Doe";
        var age = 30;
        var region = "Lima";
        var userId = "user-123";

        // Act
        var profile = Profile.create(
                name,
                age,
                region,
                new UserId(userId)
        );

        // Assert
        assertEquals(name, profile.getName());
    }

    @Test
    void create_shouldNotAllowCreateProfile_whenAgeIsNegative() {

        // Arrange
        var name = "John Doe";
        var age = -30;
        var region = "Lima";
        var userId = "user-123";

        // Act & Assert
        assertThrows(InvalidAgeException.class, () ->
                Profile.create(
                        name,
                        age,
                        region,
                        new UserId(userId)
                )
        );
    }

    @Test
    void create_shouldNotAllowCreateProfile_whenAgeIsHuge() {

        // Arrange
        var name = "John Doe";
        var hugeAge = 1000;
        var region = "Lima";
        var userId = "user-123";

        // Act & Assert
        assertThrows(InvalidAgeException.class, () ->
                Profile.create(
                        name,
                        hugeAge,
                        region,
                        new UserId(userId)
                )
        );
    }

    @Test
    void create_shouldNotAllowCreateProfile_whenRegionDoesNotExist() {

        // Arrange
        var name = "John Doe";
        var normalAge = 45;
        var region = "La Luna";
        var userId = "user-123";

        // Act & Assert
        assertThrows(InvalidRegionException.class, () ->
                Profile.create(
                        name,
                        normalAge,
                        region,
                        new UserId(userId)
                )
        );
    }

    @Test
    void update_shouldAllowUpdateProfile_whenArgumentsAreValid() {

        // Arrange
        var name = "Jonny Walker";
        var age = 30;
        var region = "Lima";
        var userId = "user-123";

        var profile = Profile.create(
                name,
                age,
                region,
                new UserId(userId)
        );

        var newName = "Jone Doe";

        // Act
        profile.update(newName);

        // Assert
        assertEquals(newName, profile.getName());
    }

    @Test
    void update_shouldNotAllowUpdateProfile_whenNameIsInvalid() {

        // Arrange
        var name = "Jonny Walker";
        var age = 30;
        var region = "Lima";
        var userId = "user-123";

        var profile = Profile.create(
                name,
                age,
                region,
                new UserId(userId)
        );

        var newName = "   ";

        // Act & Assert
        assertThrows(InvalidProfileNameException.class, () -> profile.update(newName));
    }
}
