package com.jrzln.mibalanceapi.wallets.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.application.acl.ExternalAuthenticationService;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.WalletSaveFailedException;
import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetAllWalletsByUserIdQuery;
import com.jrzln.mibalanceapi.wallets.domain.services.WalletCommandService;
import com.jrzln.mibalanceapi.wallets.domain.services.WalletQueryService;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers.RegisterWalletCommandFromResourceAssembler;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers.WalletDetailWrapperResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers.WalletResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.requests.RegisterWalletResource;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletDetailWrapperResource;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserWalletsController
 *
 * <p>
 *     This controller is responsible for handling wallet-related requests for users.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>GET /api/v1/users/{userId}/wallets</li>
 *         <li>POST /api/v1/users/{userId}/wallets</li>
 *     </ul>
 * </p>
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/v1/users/{userId}/wallets")
@Tag(name = "Users")
public class UserWalletsController {

    private final ExternalAuthenticationService authenticationService;

    private final WalletCommandService walletCommandService;
    private final WalletQueryService walletQueryService;

    /**
     * Constructor for UserWalletsController.
     *
     * @param authenticationService the external authentication service to verify user existence
     * @param walletCommandService the wallet command service to handle wallet commands
     * @param walletQueryService the wallet query service to handle wallet queries
     */
    public UserWalletsController(ExternalAuthenticationService authenticationService, WalletCommandService walletCommandService, WalletQueryService walletQueryService) {
        this.authenticationService = authenticationService;
        this.walletCommandService = walletCommandService;
        this.walletQueryService = walletQueryService;
    }

    /**
     * Endpoint to get all wallets by user id.
     *
     * @param userId the ID of the user whose wallets are to be retrieved
     * @return the {@link ResponseEntity} containing the {@link WalletDetailWrapperResource} or an error status
     */
    @GetMapping
    @Operation(summary = "Get wallets by user id", description = "Get all wallets associated with the given user id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallets retrieved successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = WalletDetailWrapperResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful Response",
                                            summary = "An example of a successful response containing multiple wallets.",
                                            value = """
                                                    {
                                                        "wallets": [
                                                            {
                                                                "walletId": "695c501f4d228c6a2bf89b77",
                                                                "name": "YAPE",
                                                                "type": "SAVINGS",
                                                                "balance": 100.5,
                                                                "currency": "PEN",
                                                                "registeredAt": "2023-10-05T14:48:00.000Z",
                                                            },
                                                            {
                                                                "walletId": "9832ndkdk",
                                                                "name": "BCP",
                                                                "type": "CHECKING",
                                                                "balance": 3200.00,
                                                                "currency": "USD",
                                                                "registeredAt": "2023-11-15T10:30:00.000Z"
                                                            }
                                                        ],
                                                        "totalCount": 2,
                                                        "userId": "6959b139b6c5058d7b5c2280"
                                                    }
                                                    """,
                                            description = "This response indicates that the wallets were successfully retrieved for the user."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No wallets found for the given user id.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "No Content Response",
                                            summary = "An example of a no content response when no wallets are found.",
                                            value = """
                                                    {
                                                        "wallets": [],
                                                        "totalCount": 0,
                                                        "userId": "6959b139b6c5058d7b5c2280"
                                                    }
                                                    """,
                                            description = "This response indicates that the request was successful but there are no wallets associated with the given user id."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user id supplied.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Bad Request Response",
                                            summary = "An example of a bad request response when the user id is invalid.",
                                            value = """
                                                    {
                                                        "timestamp": "2023-10-05T15:00:00.000Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "message": "User ID cannot be null or blank.",
                                                        "path": "/api/v1/users/{userId}/wallets"
                                                    }
                                                    """,
                                            description = "This response indicates that the user id provided in the request is invalid."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "unauthorized."
            )
    })
    public ResponseEntity<WalletDetailWrapperResource> getAllWalletsByUserId(
            @Parameter(
                    description = "The ID of the user whose wallets are to be retrieved.",
                    example = "6959b139b6c5058d7b5c2280",
                    required = true
            )
            @PathVariable String userId
    ) {

        try {
            if (isUserIdNotValid(userId) && doesUserNotExist(userId)) {
                return ResponseEntity.badRequest().build();
            }

            var targetUserId = new UserId(userId);
            var getAllWalletsByUserIdQuery = new GetAllWalletsByUserIdQuery(targetUserId);
            var wallets = walletQueryService.handle(getAllWalletsByUserIdQuery);

            if (wallets.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            var walletDetailWrapper = WalletDetailWrapperResourceFromEntityAssembler.toResourceFromEntity(wallets);
            return ResponseEntity.ok(walletDetailWrapper);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to register a new wallet for a user.
     *
     * @param userId the ID of the user to register the wallet for
     * @param resource the {@link RegisterWalletResource} containing wallet details
     * @return the {@link ResponseEntity} containing the registered {@link WalletResource} or an error status
     */
    @PostMapping
    @Operation(summary = "Register wallet for user", description = "Register a new wallet for the given user id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Wallet registered successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = WalletResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful Response",
                                            summary = "An example of a successful wallet registration response.",
                                            value = """
                                                    {
                                                        "walletId": "695c501f4d228c6a2bf89b77",
                                                        "name": "YAPE",
                                                        "type": "SAVINGS",
                                                        "balance": 100.50,
                                                        "currency": "PEN",
                                                        "registeredAt": "2026-01-05T14:48:00.000Z",
                                                        "userId": "6959b139b6c5058d7b5c2280"
                                                    }
                                                    """,
                                            description = "This response indicates that the wallet was successfully registered for the user."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user id or wallet data supplied.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Bad Request Response",
                                            summary = "An example of a bad request response when the user id or wallet data is invalid.",
                                            value = """
                                                    {
                                                        "timestamp": "2026-01-05T15:00:00.000Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "message": "User ID cannot be null or blank.",
                                                        "path": "/api/v1/users/{userId}/wallets"
                                                    }
                                                    """,
                                            description = "This response indicates that the user id or wallet data provided in the request is invalid."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized."
            )
    })
    public ResponseEntity<WalletResource> registerWallet(
            @Parameter(
                    description = "The ID of the user to register the wallet for.",
                    example = "6959b139b6c5058d7b5c2280",
                    required = true
            )
            @PathVariable String userId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The wallet registration details.",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterWalletResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Request Body Example",
                                            summary = "An example of a wallet registration request body.",
                                            value = """
                                                    {
                                                        "name": "YAPE",
                                                        "type": "SAVINGS",
                                                        "initialBalance": 100.50,
                                                        "currency": "PEN"
                                                    }
                                                    """,
                                            description = "This request body is used to register a new wallet for the user."
                                    ),
                                    @ExampleObject(
                                            name = "Another Request Body Example",
                                            summary = "Another example of a wallet registration request body.",
                                            value = """
                                                    {
                                                        "name": "BCP",
                                                        "type": "CHECKING",
                                                        "initialBalance": 2500.00,
                                                        "currency": "USD"
                                                    }
                                                    """,
                                            description = "This request body is used to register a different type of wallet for the user."
                                    )
                            }
                    )
            )
            @RequestBody RegisterWalletResource resource
    ) {

        try {
            if (isUserIdNotValid(userId) && doesUserNotExist(userId)) {
                return ResponseEntity.badRequest().build();
            }

            var registerWalletCommand = RegisterWalletCommandFromResourceAssembler.toCommandFromResource(userId, resource);
            var wallet = walletCommandService.handle(registerWalletCommand);

            if (wallet.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var walletResource = WalletResourceFromEntityAssembler.toResourceFromEntity(wallet.get());
            return new ResponseEntity<>(walletResource, HttpStatus.CREATED);
        } catch (WalletSaveFailedException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method to check if a userId is not valid.
     *
     * @param userId the ID of the user to check
     * @return true if the userId is not valid, false otherwise
     */
    private boolean isUserIdNotValid(String userId) {
        return userId == null || userId.isBlank();
    }

    /**
     * Method to check if a user does not exist.
     *
     * @param userId the ID of the user to check
     * @return true if the user does not exist, false otherwise
     */
    private boolean doesUserNotExist(String userId) {
        return !authenticationService.verifyIfUserExists(userId);
    }
}
