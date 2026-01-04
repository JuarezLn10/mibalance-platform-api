package com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.iam.domain.model.commands.SignInCommand;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests.SignInResource;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;

/**
 * Assembler class to convert {@link SignInResource} to {@link SignInCommand}.
 */
public class SignInCommandFromResourceAssembler {

    /**
     * Converts a {@link SignInResource} to a {@link SignInCommand}.
     *
     * @param resource the sign-in resource
     * @return the sign-in command
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        var username = new Email(resource.username());

        return new SignInCommand(
                username,
                resource.password()
        );
    }
}
