package com.jrzln.mibalanceapi.iam.domain.services;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByUserNameQuery;

import java.util.Optional;

/**
 * Service interface for querying user information.
 * <p>
 *     Handles queries related to user retrieval by ID or username.
 * </p>
 */
public interface UserQueryService {

    /**
     * Handles the GetUserByIdQuery to retrieve a user by their ID.
     *
     * @param query The query containing the user ID.
     * @return An Optional containing the User if found, otherwise empty.
     *
     * @see GetUserByIdQuery
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handles the GetUserByUserNameQuery to retrieve a user by their username.
     *
     * @param query The query containing the username.
     * @return An Optional containing the User if found, otherwise empty.
     *
     * @see GetUserByUserNameQuery
     */
    Optional<User> handle(GetUserByUserNameQuery query);
}
