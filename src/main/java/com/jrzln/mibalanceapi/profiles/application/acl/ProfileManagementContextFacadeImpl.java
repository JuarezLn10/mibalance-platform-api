package com.jrzln.mibalanceapi.profiles.application.acl;

import com.jrzln.mibalanceapi.profiles.domain.model.commands.CreateProfileCommand;
import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.ProfileSaveFailedException;
import com.jrzln.mibalanceapi.profiles.domain.services.ProfileCommandService;
import com.jrzln.mibalanceapi.profiles.interfaces.acl.ProfileManagementContextFacade;
import com.jrzln.mibalanceapi.shared.domain.model.aggregates.AuditableDocument;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link ProfileManagementContextFacade} interface for managing profiles.
 */
@Service
public class ProfileManagementContextFacadeImpl implements ProfileManagementContextFacade {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfileManagementContextFacadeImpl.class);

    private final ProfileCommandService profileCommandService;

    /**
     * Constructor for ProfileManagementContextFacadeImpl.
     *
     * @param profileCommandService the profile command service
     */
    public ProfileManagementContextFacadeImpl(ProfileCommandService profileCommandService) {
        this.profileCommandService = profileCommandService;
    }

    /**
     * Creates a new profile.
     *
     * @param name   the name of the profile
     * @param age    the age of the profile
     * @param region the region of the profile
     * @param userId the user ID associated with the profile
     * @return an Optional containing the ID of the created profile, or empty if creation failed
     */
    @Override
    public Optional<String> createProfile(String name, Integer age, String region, String userId) {

        try {
            var createProfileCommand = new CreateProfileCommand(name, age, region, new UserId(userId));
            var profile = profileCommandService.handle(createProfileCommand);
            if (profile.isEmpty()) {
                throw new ProfileSaveFailedException("Failed to create profile for user ID: " + userId);
            }

            return profile.map(AuditableDocument::getId);

        } catch (RuntimeException e) {
            LOGGER.error("Failed to create profile for user ID: {}", userId);
            throw new ProfileSaveFailedException("Failed to create profile: " + e.getMessage());
        }
    }
}
