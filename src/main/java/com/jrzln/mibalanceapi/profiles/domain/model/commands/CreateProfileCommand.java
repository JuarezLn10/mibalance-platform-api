package com.jrzln.mibalanceapi.profiles.domain.model.commands;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;

/**
 * Command to create a new profile.
 *
 * @param name the profile name
 * @param age the profile age
 * @param region the profile region
 * @param userId the associated user ID
 */
public record CreateProfileCommand(String name, Integer age, String region, UserId userId) {}