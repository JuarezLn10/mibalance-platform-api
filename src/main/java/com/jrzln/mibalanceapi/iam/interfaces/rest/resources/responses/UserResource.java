package com.jrzln.mibalanceapi.iam.interfaces.rest.resources.responses;

/**
 * User resource response
 *
 * @param id the unique identifier of the user
 * @param username the email of the user
 *
 * @see com.jrzln.mibalanceapi.iam.domain.model.aggregates.User
 */
public record UserResource(
        String id,
        String username
) {}