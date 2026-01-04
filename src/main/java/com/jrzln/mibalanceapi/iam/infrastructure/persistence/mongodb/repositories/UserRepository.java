package com.jrzln.mibalanceapi.iam.infrastructure.persistence.mongodb.repositories;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in MongoDB.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a user by their username (email).
     *
     * @param username the email of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(Email username);

    /**
     * Check if a user exists by their username (email).
     *
     * @param username the email of the user
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(Email username);
}
