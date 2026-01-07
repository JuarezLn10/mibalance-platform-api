package com.jrzln.mibalanceapi.profiles.domain.model.aggregates;

import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidAgeException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidProfileNameException;
import com.jrzln.mibalanceapi.profiles.domain.model.valueobjects.RegionNames;
import com.jrzln.mibalanceapi.shared.domain.model.aggregates.AuditableDocument;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Profile aggregate root representing a user profile.
 */
@Getter
@Document(collection = "profiles")
public class Profile extends AuditableDocument {

    // Profile name
    @Field(value = "name")
    private String name;

    // Profile age
    @Field(value = "age")
    private Integer age;

    // Profile region
    @Field(value = "region")
    private RegionNames region;

    // Associated user ID
    @Indexed(unique = true)
    @Field(value = "user_id")
    @Valid
    private UserId userId;

    // Protected no-argument constructor for framework use
    protected Profile() {}

    // Constructor to initialize Profile with name, age, region, and userId
    private Profile(String name, Integer age, String region, UserId userId) {
        this.name = name;
        this.age = age;
        this.region = RegionNames.fromString(region);
        this.userId = userId;
    }

    /**
     * Static factory method to create a new Profile instance.
     *
     * @param name the profile name
     * @param age the profile age
     * @param region the profile region
     * @param userId the associated user ID
     * @return a new Profile instance
     */
    public static Profile create(String name, Integer age, String region, UserId userId) {
        if (!isNameValid(name)) {
            throw new InvalidProfileNameException("Profile name cannot be null or empty");
        }

        if (!isAgeValid(age)) {
            throw new InvalidAgeException("Age must be a non-negative integer");
        }

        return new Profile(name, age, region, userId);
    }

    /**
     * Private helper method to validate age.
     *
     * @param age the age to validate
     * @return true if age is valid, false otherwise
     */
    private static boolean isAgeValid(Integer age) {
        return age != null && age >= 0 && age <= 99;
    }

    /**
     * Updates the profile name.
     *
     * @param name the new profile name
     * @throws InvalidProfileNameException if the name is null or empty
     */
    public void update(String name) {
        if (!isNameValid(name)) {
            throw new InvalidProfileNameException("Profile name cannot be null or empty");
        }

        this.name = name;
    }

    /**
     * Private helper method to validate name.
     *
     * @param name the name to validate
     * @return true if name is valid, false otherwise
     */
    private static boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
