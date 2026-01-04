package com.jrzln.mibalanceapi.iam.domain.model.queries;

/**
 * @summary
 * Query to get a user by its id.
 *
 * @param id the id of the user.
 */
public record GetUserByIdQuery(String id) {}
