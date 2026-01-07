package com.jrzln.mibalanceapi.profiles.domain.model.queries;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;

/**
 * Query to get a profile by user ID.
 *
 * @param userId the ID of the user whose profile is to be retrieved
 */
public record GetProfileByUserIdQuery(UserId userId) {}