package com.jrzln.mibalanceapi.profiles.interfaces.acl;

import java.util.Optional;

/**
 * Facade interface for profile management context-
 */
public interface ProfileManagementContextFacade {

    /**
     * Creates a new profile.
     *
     * @param name the name of the profile
     * @param age the age of the profile
     * @param region the region of the profile
     * @param userId the user ID associated with the profile
     * @return an Optional containing the ID of the created profile, or empty if creation failed
     */
    Optional<String> createProfile(String name, Integer age, String region, String userId);
}
