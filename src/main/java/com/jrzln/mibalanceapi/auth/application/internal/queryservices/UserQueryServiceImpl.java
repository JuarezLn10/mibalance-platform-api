package com.jrzln.mibalanceapi.auth.application.internal.queryservices;

import com.jrzln.mibalanceapi.auth.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.auth.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.auth.domain.model.queries.GetUserByUserNameQuery;
import com.jrzln.mibalanceapi.auth.domain.services.UserQueryService;
import com.jrzln.mibalanceapi.auth.infrastructure.persistence.mongodb.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link UserQueryService} interface for handling user-related queries.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles the GetUserByIdQuery to retrieve a user by their ID.
     *
     * @param query The query containing the user ID.
     * @return An Optional containing the User if found, otherwise empty.
     *
     * @see GetUserByIdQuery
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {

        return userRepository.findById(query.id());
    }

    /**
     * Handles the GetUserByUserNameQuery to retrieve a user by their username.
     *
     * @param query The query containing the username.
     * @return An Optional containing the User if found, otherwise empty.
     *
     * @see GetUserByUserNameQuery
     */
    @Override
    public Optional<User> handle(GetUserByUserNameQuery query) {

        return userRepository.findByUsername(query.username());
    }
}
