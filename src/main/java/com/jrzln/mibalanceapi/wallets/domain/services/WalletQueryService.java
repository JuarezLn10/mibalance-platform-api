package com.jrzln.mibalanceapi.wallets.domain.services;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetAllWalletsByUserIdQuery;
import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetWalletByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling wallet queries.
 */
public interface WalletQueryService {

    /**
     * Handles the {@link GetWalletByIdQuery} to retrieve a wallet by its ID.
     *
     * @param query the query containing the wallet ID
     * @return an Optional containing the Wallet if found, or empty if not found
     */
    Optional<Wallet> handle(GetWalletByIdQuery query);

    /**
     * Handles the {@link GetAllWalletsByUserIdQuery} to retrieve all wallets for a specific user.
     *
     * @param query the query containing the user ID
     * @return a list of Wallets associated with the user
     */
    List<Wallet> handle(GetAllWalletsByUserIdQuery query);
}
