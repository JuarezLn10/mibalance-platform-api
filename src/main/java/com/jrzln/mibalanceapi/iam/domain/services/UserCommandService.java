package com.jrzln.mibalanceapi.iam.domain.services;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.commands.SignInCommand;
import com.jrzln.mibalanceapi.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.Optional;

/**
 * Service interface for handling user commands such as sign-in and sign-up.
 * <p>
 *     This interface defines methods for processing user authentication commands.
 * </p>
 */
public interface UserCommandService {

    /**
     * Handles the sign-in command.
     *
     * @param command the sign-in command containing user credentials
     * @return an Optional containing an ImmutableTriple with the User, access token, and user id if successful; otherwise, an empty Optional
     */
    Optional<ImmutableTriple<User, String, String>> handle(SignInCommand command);

    /**
     * Handles the sign-up command.
     *
     * @param command the sign-up command containing user registration details
     * @return an Optional containing the created User if successful; otherwise, an empty Optional
     */
    Optional<User> handle(SignUpCommand command);
}
