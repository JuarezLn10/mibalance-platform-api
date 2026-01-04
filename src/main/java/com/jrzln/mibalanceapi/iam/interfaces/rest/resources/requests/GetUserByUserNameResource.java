package com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests;

/**
 * Request resource for getting a user by username.
 *
 * @param username the username of the user to retrieve
 */
public record GetUserByUserNameResource(String username) {}