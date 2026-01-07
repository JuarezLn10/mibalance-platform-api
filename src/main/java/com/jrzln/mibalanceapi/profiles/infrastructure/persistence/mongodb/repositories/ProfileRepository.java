package com.jrzln.mibalanceapi.profiles.infrastructure.persistence.mongodb.repositories;

import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Profile entities.
 */
@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    /**
     * Find a profile by user ID.
     *
     * @param userId the user ID
     * @return an Optional containing the found profile, or empty if not found
     */
    Optional<Profile> findByUserId(UserId userId);
}
