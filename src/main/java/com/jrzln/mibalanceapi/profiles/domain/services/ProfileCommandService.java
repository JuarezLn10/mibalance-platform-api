package com.jrzln.mibalanceapi.profiles.domain.services;

import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.profiles.domain.model.commands.CreateProfileCommand;
import com.jrzln.mibalanceapi.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

/**
 * Service interface for handling profile-related commands.
 */
public interface ProfileCommandService {

    /**
     * Handler for creating a new profile.
     *
     * @param command the command containing profile creation data
     * @return an Optional containing the created Profile, or empty if creation failed
     */
    Optional<Profile> handle(CreateProfileCommand command);

    /**
     * Handler for updating an existing profile.
     *
     * @param command the command containing profile update data
     * @return an Optional containing the updated Profile, or empty if update failed
     */
    Optional<Profile> handle(UpdateProfileCommand command);
}
