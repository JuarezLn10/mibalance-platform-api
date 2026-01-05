package com.jrzln.mibalanceapi.iam.interfaces.acl;

/**
 * Facade interface for authentication context operations.
 */
public interface AuthenticationContextFacade {

    /**
     * Verifies if a user exists by their ID.
     *
     * @param userId the ID of the user to verify
     * @return true if the user exists, false otherwise
     */
    boolean verifyIfUserExistsById(String userId);
}
