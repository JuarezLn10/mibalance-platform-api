package com.jrzln.mibalanceapi.iam.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.iam.domain.model.aggregates.User;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByIdQuery;
import com.jrzln.mibalanceapi.iam.domain.model.queries.GetUserByUserNameQuery;
import com.jrzln.mibalanceapi.iam.domain.services.UserQueryService;
import com.jrzln.mibalanceapi.iam.interfaces.rest.assemblers.UserResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.requests.GetUserByUserNameResource;
import com.jrzln.mibalanceapi.iam.interfaces.rest.resources.responses.UserResource;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UsersController
 *
 * <p>
 *     This controller is responsible for handling user-related requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>GET /api/v1/users/{user-id}</li>
 *         <li>GET /api/v1/users (by its username)</li>
 *     </ul>
 * </p>
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Available User Endpoints")
public class UsersController {

    private final UserQueryService userQueryService;

    /**
     * Constructor for UsersController.
     *
     * @param userQueryService the user query service to handle user queries
     */
    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Endpoint to get a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return ResponseEntity containing the user resource or a not found status
     *
     * @see User
     */
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get user by id", description = "Get the user with the given id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful Response",
                                            summary = "An example of a successful response",
                                            value = """
                                                    {
                                                        "userId": "6959b139b6c5058d7b5c2280",
                                                        "username": "nicolas@gmail.com"
                                                    }
                                                    """,
                                            description = "The response will contain the user details."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized."
            )
    })
    public ResponseEntity<UserResource> getUserById(
            @Parameter(
                    description = "The ID of the user to retrieve.",
                    example = "6959b139b6c5058d7b5c2280",
                    required = true
            )
            @PathVariable String userId
    ) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Endpoint to get a user by their email.
     *
     * @param resource the request resource containing the username (email)
     * @return ResponseEntity containing the user resource or a not found status
     *
     * @see User
     */
    @GetMapping
    @Operation(summary = "Get user by email", description = "Get the user with the given email.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful Response",
                                            summary = "An example of a successful response",
                                            value = """
                                                    {
                                                        "id": "6959b139b6c5058d7b5c2280",
                                                        "username": "nicolas@gmail.com"
                                                    }
                                                    """,
                                            description = "A successful response containing the user details."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with the given username was not found."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized."
            )
    })
    public ResponseEntity<UserResource> getUserByEmail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "A request resource containing the username of the user to retrieve.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Good fetch",
                                            summary = "A valid username fetch example",
                                            value = """
                                                    {
                                                        "username": "nicolas@gmail.com"
                                                    }
                                                    """,
                                            description = "The username (email) of an existing user in the system."
                                    ),
                                    @ExampleObject(
                                            name = "Bad fetch",
                                            summary = "An invalid username fetch example",
                                            value = """
                                                    {
                                                        "username": "hello_world"
                                                    }
                                                    """,
                                            description = "Username (email) has an invalid format so there won't be any user with that username."
                                    )
                            }
                    )
            )
            @RequestParam GetUserByUserNameResource resource
    ) {
        var email = new Email(resource.username());
        var getUserByUserNameQuery = new GetUserByUserNameQuery(email);
        var user = userQueryService.handle(getUserByUserNameQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}
