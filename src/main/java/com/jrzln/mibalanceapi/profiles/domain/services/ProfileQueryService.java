package com.jrzln.mibalanceapi.profiles.domain.services;

import com.jrzln.mibalanceapi.profiles.domain.model.aggregates.Profile;
import com.jrzln.mibalanceapi.profiles.domain.model.queries.GetProfileByUserIdQuery;

import java.util.Optional;

/**
 * Service interface for handling profile queries.
 */
public interface ProfileQueryService {

    /**
     * Handler for {@link GetProfileByUserIdQuery}.
     *
     * @param query the query to handle
     * @return an Optional containing the Profile if found, otherwise empty
     */
    Optional<Profile> handle(GetProfileByUserIdQuery query);
}
