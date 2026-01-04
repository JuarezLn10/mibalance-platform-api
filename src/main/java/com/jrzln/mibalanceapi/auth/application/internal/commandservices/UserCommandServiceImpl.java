package com.jrzln.mibalanceapi.auth.application.internal.commandservices;

import com.jrzln.mibalanceapi.auth.application.internal.outboundservices.hashing.HashingService;
import com.jrzln.mibalanceapi.auth.application.internal.outboundservices.tokens.TokenService;
import com.jrzln.mibalanceapi.auth.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.auth.domain.model.commands.SignInCommand;
import com.jrzln.mibalanceapi.auth.domain.model.commands.SignUpCommand;
import com.jrzln.mibalanceapi.auth.domain.model.exceptions.InvalidPasswordException;
import com.jrzln.mibalanceapi.auth.domain.model.exceptions.UserNotFoundException;
import com.jrzln.mibalanceapi.auth.domain.model.exceptions.UserSaveFailedException;
import com.jrzln.mibalanceapi.auth.domain.model.exceptions.UsernameAlreadyExistsException;
import com.jrzln.mibalanceapi.auth.domain.services.UserCommandService;
import com.jrzln.mibalanceapi.auth.infrastructure.persistence.mongodb.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link UserCommandService} interface for handling user-related commands.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserCommandServiceImpl.class);

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    /**
     * Handles the sign-in command.
     *
     * @param command the sign-in command containing user credentials
     * @return an Optional containing an ImmutableTriple with the User, access token, and user id if successful; otherwise, an empty Optional
     */
    @Override
    public Optional<ImmutableTriple<User, String, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());

        if (user.isEmpty()) {
            throw new UserNotFoundException(command.username().toString());
        }

        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            throw new InvalidPasswordException(user.get().getUsername().email());
        }

        var token = tokenService.generateToken(user.get().getUsername().email());

        return Optional.of(ImmutableTriple.of(user.get(), token, user.get().getId()));
    }

    /**
     * Handles the sign-up command.
     *
     * @param command the sign-up command containing user registration details
     * @return an Optional containing the created User if successful; otherwise, an empty Optional
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new UsernameAlreadyExistsException(command.username().email());

        var user = new User(command.username(), hashingService.encode(command.password()));

        try {
            userRepository.save(user);
        } catch (DataAccessException ex) {
            LOGGER.error("Error trying to save the user {}", command.username().email(), ex);
            throw new UserSaveFailedException(command.username().email(), ex);
        }

        return Optional.of(user);
    }
}
