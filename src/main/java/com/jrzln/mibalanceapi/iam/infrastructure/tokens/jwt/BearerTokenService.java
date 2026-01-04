package com.jrzln.mibalanceapi.iam.infrastructure.tokens.jwt;

import com.jrzln.mibalanceapi.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * Service for handling bearer token operations such as extraction and generation
 * <p>
 *     It extends the {@link TokenService} interface.
 * </p>
 */
public interface BearerTokenService extends TokenService {

    /**
     * Extracts the bearer token from the HTTP request
     *
     * @param token the HTTP request containing the bearer token
     * @return the extracted bearer token
     */
    String getBearerTokenFrom(HttpServletRequest token);

    /**
     * Generates a token based on the authentication object
     *
     * @param authentication the authentication object containing user details
     * @return the generated token
     */
    String generateToken(Authentication authentication);
}
