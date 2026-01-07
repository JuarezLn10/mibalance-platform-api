package com.jrzln.mibalanceapi.profiles.application.internal.commandservices;

import com.jrzln.mibalanceapi.profiles.application.acl.ExternalAuthenticationService;
import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.profiles.domain.model.commands.CreateProfileCommand;
import com.jrzln.mibalanceapi.profiles.domain.model.commands.UpdateProfileCommand;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidAgeException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidProfileNameException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.ProfileSaveFailedException;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.ProfileUpdateFailedException;
import com.jrzln.mibalanceapi.profiles.domain.services.ProfileCommandService;
import com.jrzln.mibalanceapi.profiles.infrastructure.persistence.mongodb.repositories.ProfileRepository;
import com.jrzln.mibalanceapi.shared.domain.model.exceptions.InvalidUserIdException;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link ProfileCommandService} interface for handling profile-related commands.
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileCommandServiceImpl.class);

    private final ExternalAuthenticationService authenticationService;
    private final ProfileRepository profileRepository;

    /**
     * Constructor for ProfileCommandServiceImpl.
     *
     * @param authenticationService the external authentication service
     * @param profileRepository the profile repository for data access
     */
    public ProfileCommandServiceImpl(ExternalAuthenticationService authenticationService, ProfileRepository profileRepository) {
        this.authenticationService = authenticationService;
        this.profileRepository = profileRepository;
    }

    /**
     * Handler for creating a new profile.
     *
     * @param command the command containing profile creation data
     * @return an Optional containing the created Profile, or empty if creation failed
     */
    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {

        try {
            var profile = Profile.create(
                    command.name(),
                    command.age(),
                    command.region(),
                    command.userId()
            );

            profileRepository.save(profile);
            return Optional.of(profile);
        } catch (InvalidAgeException | InvalidProfileNameException | InvalidUserIdException | DataAccessException ex) {
            LOGGER.error("Failed to create profile for user ID {}: {}", command.userId().userId(), ex.getMessage());
            throw new ProfileSaveFailedException("Failed to create profile: " + ex.getMessage());
        }
    }

    /**
     * Handler for updating an existing profile.
     *
     * @param command the command containing profile update data
     * @return an Optional containing the updated Profile, or empty if update failed
     */
    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {

        try {
            if (!doesUserExist(command.userId())) {
                LOGGER.error("User ID {} does not exist", command.userId().userId());
                throw new ProfileUpdateFailedException("User ID does not exist: " + command.userId().userId());
            }

            var existingProfile = profileRepository.findByUserId(command.userId());
            if (existingProfile.isEmpty()) {
                LOGGER.error("Profile not found for user ID {}", command.userId().userId());
                throw new ProfileUpdateFailedException("Profile not found for user ID: " + command.userId().userId());
            }

            var profile = existingProfile.get();
            profile.update(command.profileName());

            profileRepository.save(profile);
            return Optional.of(profile);

        } catch (InvalidProfileNameException | DataAccessException ex) {
            LOGGER.error("Failed to update profile for user ID {}: {}", command.userId().userId(), ex.getMessage());
            throw new ProfileUpdateFailedException("Failed to update profile: " + ex.getMessage());
        }
    }

    /**
     * Checks if a user exists using the external authentication service.
     *
     * @param userId the user ID to check
     * @return true if the user exists, false otherwise
     */
    private boolean doesUserExist(UserId userId) {
        return authenticationService.verifyIfUserExists(userId.userId());
    }
}
