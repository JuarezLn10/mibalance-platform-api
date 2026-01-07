package com.jrzln.mibalanceapi.profiles.domain.model.commands;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;

/**
 * Command to update a profile.
 *
 * @param userId     the ID of the user whose profile is to be updated
 * @param profileName the new name of the profile
 */
public record UpdateProfileCommand(UserId userId, String profileName) {}