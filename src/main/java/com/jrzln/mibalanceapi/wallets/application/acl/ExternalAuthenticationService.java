package com.jrzln.mibalanceapi.wallets.application.acl;

import com.jrzln.mibalanceapi.iam.interfaces.acl.AuthenticationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Service to interact with the external authentication system.
 * This service uses the {@link AuthenticationContextFacade} to verify user existence.
 */
@Service
public class ExternalAuthenticationService {

    private final AuthenticationContextFacade authenticationContextFacade;

    /**
     * Constructor for ExternalAuthenticationService.
     *
     * @param authenticationContextFacade the authentication context facade to interact with the external authentication system
     */
    public ExternalAuthenticationService(AuthenticationContextFacade authenticationContextFacade) {
        this.authenticationContextFacade = authenticationContextFacade;
    }

    /**
     * Verifies if a user exists in the external authentication system by their user ID.
     *
     * @param userId the ID of the user to verify
     * @return true if the user exists, false otherwise
     */
    public boolean verifyIfUserExists(String userId) {
        return authenticationContextFacade.verifyIfUserExistsById(userId);
    }
}