package com.jrzln.mibalanceapi.auth.application.internal.outboundservices.tokens;

/**
 * Service for handling token operations such as generation, extraction, and validation
 */
public interface TokenService {

    /**
     * Generates a token for the given username
     *
     * @param username the username to generate the token for
     * @return the generated token
     */
    String generateToken(String username);

    /**
     * Extracts the username from the token
     *
     * @param token the token to extract the username from
     * @return the username contained in the token
     */
    String getUsernameFromToken(String token);

    /**
     * Validates if the token is still valid
     *
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     */
    boolean isTokenValid(String token);
}
