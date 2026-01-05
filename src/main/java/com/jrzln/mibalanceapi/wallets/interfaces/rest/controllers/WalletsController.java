package com.jrzln.mibalanceapi.wallets.interfaces.rest.controllers;

import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetWalletByIdQuery;
import com.jrzln.mibalanceapi.wallets.domain.services.WalletQueryService;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers.WalletResourceFromEntityAssembler;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletResource;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WalletsController
 *
 * <p>
 *     This controller is responsible for handling wallet-related requests.
 *     It exposes one endpoint:
 *     <ul>
 *         <li>GET /api/v1/wallets/{walletId}</li>
 *     </ul>
 * </p>
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/v1/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Wallets", description = "Available Wallet Endpoints")
public class WalletsController {

    private final WalletQueryService walletQueryService;

    /**
     * Constructor for WalletsController.
     *
     * @param walletQueryService the wallet query service to handle wallet queries
     */
    public WalletsController(WalletQueryService walletQueryService) {
        this.walletQueryService = walletQueryService;
    }

    /**
     * Endpoint to get a wallet by its ID.
     *
     * @param walletId the ID of the wallet to retrieve
     * @return ResponseEntity containing the wallet resource or a not found status
     */
    @GetMapping(value = "/{walletId}")
    @Operation(summary = "Get wallet by id", description = "Get the wallet with the given id.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet retrieved successfully.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = WalletResource.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Successful Response",
                                            summary = "A sample successful response",
                                            value = """
                                                    {
                                                        "walletId": "3129923i31",
                                                        "name": "YAPE",
                                                        "type": "SAVINGS",
                                                        "balance": 1500.75,
                                                        "currency": "PEN",
                                                        "registeredAt": "2023-10-05T14:48:00.000Z",
                                                        "userId": "6959b139b6c5058d7b5c2280"
                                                    }
                                                    """,
                                            description = "This is an example of a successful response when retrieving a wallet by its ID."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid wallet id supplied.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Bad Request Response",
                                            summary = "A sample bad request response",
                                            value = """
                                                    {
                                                        "timestamp": "2023-10-05T15:00:00.000Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "message": "Wallet ID cannot be null or blank.",
                                                        "path": "/api/v1/wallets/{walletId}"
                                                    }
                                                    """,
                                            description = "This is an example of a bad request response when the wallet ID is invalid."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Not Found Response",
                                            summary = "A sample not found response",
                                            value = """
                                                    {
                                                        "timestamp": "2023-10-05T15:05:00.000Z",
                                                        "status": 404,
                                                        "error": "Not Found",
                                                        "message": "Wallet with ID '3129923i31' not found.",
                                                        "path": "/api/v1/wallets/3129923i31"
                                                    }
                                                    """,
                                            description = "This is an example of a not found response when the wallet does not exist."
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized."
            )
        }
    )
    public ResponseEntity<WalletResource> getWalletById(
            @Parameter(
                    description = "The ID of the wallet to retrieve",
                    example = "3129923i31",
                    required = true
            )
            @PathVariable String walletId
    ) {

        if (walletId == null || walletId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var getWalletByIdQuery = new GetWalletByIdQuery(walletId);
        var wallet = walletQueryService.handle(getWalletByIdQuery);
        if (wallet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var walletResource = WalletResourceFromEntityAssembler.toResourceFromEntity(wallet.get());
        return ResponseEntity.ok(walletResource);
    }
}