package com.jrzln.mibalanceapi.auth.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.auth.domain.services.UserCommandService;
import com.jrzln.mibalanceapi.auth.interfaces.rest.assemblers.AuthenticatedUserResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.auth.interfaces.rest.assemblers.SignInCommandFromResourceAssembler;
import com.jrzln.mibalanceapi.auth.interfaces.rest.assemblers.SignUpCommandFromResourceAssembler;
import com.jrzln.mibalanceapi.auth.interfaces.rest.assemblers.UserResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.auth.interfaces.rest.resources.requests.SignInResource;
import com.jrzln.mibalanceapi.auth.interfaces.rest.resources.requests.SignUpResource;
import com.jrzln.mibalanceapi.auth.interfaces.rest.resources.responses.AuthenticatedUserResource;
import com.jrzln.mibalanceapi.auth.interfaces.rest.resources.responses.UserResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 *
 * <p>
 *     This controller is responsible for handling authentication-related requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    /**
     * Constructor for AuthenticationController.
     *
     * @param userCommandService the user command service to handle authentication commands
     */
    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Endpoint to sign in a user.
     *
     * @param resource the sign-in request resource containing user credentials
     * @return ResponseEntity containing the authenticated user resource or a not found status
     */
    @PostMapping(value = "/sign-in")
    @Operation(summary = "Sign-in", description = "Sign-in with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource resource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedUserResource =
                AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
                        authenticatedUser.get().getLeft(),
                        authenticatedUser.get().getMiddle()
                );

        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Endpoint to sign up a new user.
     *
     * @param resource the sign-up request resource containing user information
     * @return ResponseEntity containing the created user resource or a bad request status
     */
    @PostMapping(value = "/sign-up")
    @Operation(summary = "Sign-up", description = "Sign-up with the provided user information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request.")})
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource resource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
