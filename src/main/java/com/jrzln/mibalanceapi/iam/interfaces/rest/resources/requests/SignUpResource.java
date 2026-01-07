package com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests;

/**
 * Resource containing the data required for user sign-up.
 *
 * @param username the username of the user to be registered
 * @param password the password of the user to be registered
 *
 * @param name     the name of the user to be registered. This will be used to create the user's profile.
 * @param age      the age of the user to be registered. This will be used to create the user's profile.
 * @param region   the region of the user to be registered. This will be used to create the user's profile.
 */
public record SignUpResource(
        String username,
        String password,
        String name,
        Integer age,
        String region
) {}