package com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses;

import java.time.Instant;

/**
 * Resource representing a Wallet for REST responses.
 *
 * @param walletId the unique identifier of the wallet
 * @param name the name of the wallet
 * @param type the type of the wallet
 * @param balance the current balance of the wallet
 * @param initialBalance the initial balance of the wallet
 * @param currencyCode the currency code of the wallet
 * @param registeredAt the timestamp when the wallet was registered
 * @param userId the identifier of the user associated with the wallet
 */
public record WalletResource(
        String walletId,
        String name,
        String type,
        Double balance,
        Double initialBalance,
        String currencyCode,
        Instant registeredAt,
        String userId
) {}