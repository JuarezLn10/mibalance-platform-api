package com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses;

import java.time.Instant;

/**
 * Resource representing detailed information about a Wallet.
 * This resource is used in the {@link WalletDetailWrapperResource} to provide detailed wallet data.
 *
 * @param walletId the unique identifier of the wallet
 * @param name the name of the wallet
 * @param type the type of the wallet
 * @param balance the current balance of the wallet
 * @param initialBalance the initial balance of the wallet
 * @param currencyCode the currency code of the wallet
 * @param registeredAt the timestamp when the wallet was registered
 */
public record WalletDetailItemResource(
        String walletId,
        String name,
        String type,
        Double balance,
        Double initialBalance,
        String currencyCode,
        Instant registeredAt
) {}