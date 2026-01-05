package com.jrzln.mibalanceapi.wallets.application.internal.queryservices;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetAllWalletsByUserIdQuery;
import com.jrzln.mibalanceapi.wallets.domain.model.queries.GetWalletByIdQuery;
import com.jrzln.mibalanceapi.wallets.domain.services.WalletQueryService;
import com.jrzln.mibalanceapi.wallets.infrastructure.persistence.mongodb.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link WalletQueryService} interface for handling wallet queries.
 * This service provides methods to retrieve wallet information based on specific queries.
 */
@Service
public class WalletQueryServiceImpl implements WalletQueryService {

    private final WalletRepository walletRepository;

    // Constructor.
    public WalletQueryServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Handles the {@link GetWalletByIdQuery} to retrieve a wallet by its ID.
     *
     * @param query the query containing the wallet ID
     * @return an Optional containing the Wallet if found, or empty if not found
     */
    @Override
    public Optional<Wallet> handle(GetWalletByIdQuery query) {
        return walletRepository.findById(query.walletId());
    }

    /**
     * Handles the {@link GetAllWalletsByUserIdQuery} to retrieve all wallets for a specific user.
     *
     * @param query the query containing the user ID
     * @return a list of Wallets associated with the user
     */
    @Override
    public List<Wallet> handle(GetAllWalletsByUserIdQuery query) {
        return walletRepository.findByUserId(query.userId());
    }
}
