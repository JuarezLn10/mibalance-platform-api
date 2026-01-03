package com.jrzln.mibalanceapi.auth.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.auth.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.auth.interfaces.rest.resources.responses.UserResource;

/**
 * Assembler class to convert {@link User} entity to {@link UserResource}.
 */
public class UserResourceFromEntityAssembler {

    /**
     * Converts a {@link User} entity to a {@link UserResource}.
     *
     * @param entity the User entity to convert
     * @return the corresponding UserResource
     */
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
                entity.getId(),
                entity.getUsername().email()
        );
    }
}
