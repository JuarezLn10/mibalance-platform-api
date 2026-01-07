package com.jrzln.mibalanceapi.profiles.application.internal.queryservices;

import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.jrzln.mibalanceapi.profiles.domain.services.ProfileQueryService;
import com.jrzln.mibalanceapi.profiles.infrastructure.persistence.mongodb.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link ProfileQueryService} interface for handling profile-related queries.
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileQueryServiceImpl.class);

    private final ProfileRepository profileRepository;

    /**
     * Constructor for ProfileQueryServiceImpl.
     *
     * @param profileRepository the profile repository for data access
     */
    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Handler for {@link GetProfileByUserIdQuery}.
     *
     * @param query the query to handle
     * @return an Optional containing the Profile if found, otherwise empty
     */
    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {

        try {
            return profileRepository.findByUserId(query.userId());
        } catch (Exception e) {
            LOGGER.error("Error retrieving profile for userId {}: {}", query.userId(), e.getMessage());
            throw new RuntimeException("Failed to retrieve profile", e);
        }
    }
}
