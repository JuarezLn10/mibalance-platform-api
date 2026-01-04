package com.jrzln.mibalanceapi.iam.domain.model.queries;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;

/**
 * @summary
 * Query to get a user by their username (email).
 *
 * @param username The email of the user to be retrieved.
 */
public record GetUserByUserNameQuery(Email username) {}
