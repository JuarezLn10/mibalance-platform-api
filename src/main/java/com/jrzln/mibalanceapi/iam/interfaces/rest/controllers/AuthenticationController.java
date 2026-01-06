package com.jrzln.mibalanceapi.iam.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.iam.domain.model.exceptions.InvalidPasswordException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UserNotFoundException;
import com.jrzln.mibalanceapi.iam.domain.model.exceptions.UsernameAlreadyExistsException;
import com.jrzln.mibalanceapi.iam.domain.services.UserCommandService;
import com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers.AuthenticatedUserResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers.SignInCommandFromResourceAssembler;
import com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers.SignUpCommandFromResourceAssembler;
import com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers.UserResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests.SignInResource;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests.SignUpResource;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.responses.AuthenticatedUserResource;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.responses.UserResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticatedUserResource.class),
                            examples = @ExampleObject(
                                    name = "Successful user sign in",
                                    summary = "An example of a successful user sign in",
                                    value = """
                                            {
                                                "username": "nicolas@gmail.com",
                                                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                                "id": "6959b139b6c5058d7b5c2280"
                                            }
                                            """,
                                    description = "Response contains the username, the access token, and the user id."
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            )
    })
    public ResponseEntity<AuthenticatedUserResource> signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credentials required to sign in a user.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SignInResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Good user sign in",
                                            summary = "A valid user sign in example",
                                            value = """
                                                    {
                                                        "username": "nicolas@gmail.com",
                                                        "password": "prueba123"
                                                    }
                                                    """,
                                            description = "A valid user registration example."
                                    ),
                                    @ExampleObject(
                                            name = "Bad user sign in (user does not exist)",
                                            summary = "A user sign in attempt with a non-existent user",
                                            value = """
                                                    {
                                                        "username": "badtest@gmail.com",
                                                        "password": "123456"
                                                    }
                                                    """,
                                            description = "The user with the provided username does not exist."
                                    ),
                                    @ExampleObject(
                                            name = "Bad user sign in (wrong password)",
                                            summary = "A user sign in attempt with an incorrect password",
                                            value = """
                                                    {
                                                        "username": "test@gmail.com",
                                                        "password": "password"
                                                    }
                                                    """,
                                            description = "The password provided is incorrect."
                                    )
                            }
                    )
            )
            @RequestBody SignInResource resource
    ) {

        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);

        try {
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
        } catch (UserNotFoundException | InvalidPasswordException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
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
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class),
                            examples = @ExampleObject(
                                    name = "Successful user sign up",
                                    summary = "An example of a successful user registration",
                                    value = """
                                            {
                                                "id": "23145535s12345",
                                                "username": "test@gmail.com"
                                            }
                                            """,
                                    description = "Response contains the user id and the username of the registered user."
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request."
            )
    })
    public ResponseEntity<UserResource> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data required to register a new user.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SignUpResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Good user registration",
                                            summary = "A valid user registration example",
                                            value = """
                                                    {
                                                        "username": "test@gmail.com",
                                                        "password": "P@ssw0rd!"
                                                    }
                                                    """,
                                            description = "A valid user registration example."
                                    ),
                                    @ExampleObject(
                                            name = "Bad user registration",
                                            summary = "An invalid user registration example",
                                            value = """
                                                    {
                                                        "username": "hello_world",
                                                        "password": "123456"
                                                    }
                                                    """,
                                            description = "Username (email) has an invalid format and password does not meet security requirements."
                                    )
                            }
                    )
            )
            @RequestBody SignUpResource resource
    ) {

        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);

        try {
            var user = userCommandService.handle(signUpCommand);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return new ResponseEntity<>(userResource, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}