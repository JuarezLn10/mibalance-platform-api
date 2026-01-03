package com.jrzln.mibalanceapi.auth.interfaces.rest.resources.requests;

/**
 * Resource containing the data required for user sign-up.
 *
 * @param username the username of the user to be registered
 * @param password the password of the user to be registered
 */
public record SignUpResource(
        String username,
        String password
) {

}