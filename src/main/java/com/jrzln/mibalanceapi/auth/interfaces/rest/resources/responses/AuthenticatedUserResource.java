package com.jrzln.mibalanceapi.auth.interfaces.rest.resources.responses;

/**
 * Resource representing an authenticated user.
 *
 * @param username the username of the authenticated user
 * @param accessToken the access token for the authenticated session
 * @param userId the unique identifier of the authenticated user
 */
public record AuthenticatedUserResource(
        String username,
        String accessToken,
        String userId) {
}