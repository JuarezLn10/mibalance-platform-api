package com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.responses.AuthenticatedUserResource;

/**
 * Assembler class to convert {@link User} entities to {@link AuthenticatedUserResource} representations.
 */
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Converts a {@link User} entity and a token into an {@link AuthenticatedUserResource}.
     *
     * @param user the user entity
     * @param token the authentication token
     * @return the authenticated user resource
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getUsername().email(),
                token,
                user.getId()
        );
    }
}
