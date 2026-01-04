package com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests;

/**
 * Resource containing the data required for user sign-in.
 *
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see com.jrzln.mibalanceapi.iam.domain.model.commands.SignInCommand
 */
public record SignInResource(
        String username,
        String password) {
}