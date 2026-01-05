package com.jrzln.mibalanceapi.iam.application.acl;

import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.iam.domain.services.UserQueryService;
import com.jrzln.mibalanceapi.iam.interfaces.acl.AuthenticationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link AuthenticationContextFacade} interface.
 * This class provides methods to interact with the authentication context,
 */
@Service
public class AuthenticationContextFacadeImpl implements AuthenticationContextFacade {

    private final UserQueryService userQueryService;

    /**
     * Constructor for AuthenticationContextFacadeImpl.
     *
     * @param userQueryService the user query service to be used
     */
    public AuthenticationContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Verifies if a user exists by their ID.
     *
     * @param userId the ID of the user to verify
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean verifyIfUserExistsById(String userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);

        return user.isPresent();
    }
}
