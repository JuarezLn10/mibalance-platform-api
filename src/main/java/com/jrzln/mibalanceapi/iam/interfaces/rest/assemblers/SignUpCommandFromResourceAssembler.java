package com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.iam.domain.model.commands.SignUpCommand;
import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests.SignUpResource;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;

/**
 * Assembler to convert {@link SignUpResource} to {@link SignUpCommand}.
 */
public class SignUpCommandFromResourceAssembler {

    /**
     * Converts a {@link SignUpResource} to a {@link SignUpCommand}.
     *
     * @param resource the sign-up resource
     * @return the sign-up command
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var username = new Email(resource.username());
        var password = new PasswordHash(resource.password());

        return new SignUpCommand(
                username,
                password
        );
    }
}