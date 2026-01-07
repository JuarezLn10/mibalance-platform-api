package com.jrzln.mibalanceapi.iam.application.acl;

import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.ProfileSaveFailedException;
import com.jrzln.mibalanceapi.profiles.interfaces.acl.ProfileManagementContextFacade;
import org.springframework.stereotype.Service;

/**
 * Service for managing user profiles by interacting with the {@link ProfileManagementContextFacade}.
 */
@Service
public class ExternalProfileManagementService {

    private final ProfileManagementContextFacade profileManagementContextFacade;

    /**
     * Constructor for ExternalProfileManagementService.
     *
     * @param profileManagementContextFacade the facade for profile management context
     */
    public ExternalProfileManagementService(ProfileManagementContextFacade profileManagementContextFacade) {
        this.profileManagementContextFacade = profileManagementContextFacade;
    }

    /**
     * Method to create a user profile by delegating to the ProfileManagementContextFacade.
     *
     * @param name the name of the user
     * @param age the age of the user
     * @param region the region of the user
     * @param userId the user ID associated with the profile
     * @return the ID of the created profile if successful
     */
    public String createProfile(String name, Integer age, String region, String userId) {
        return profileManagementContextFacade.createProfile(name, age, region, userId)
                .orElseThrow(() -> new ProfileSaveFailedException("Failed to create profile for user ID: " + userId));
    }
}
