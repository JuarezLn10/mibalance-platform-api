package com.jrzln.mibalanceapi.wallets.domain.services;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.AddBalanceToWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.DeleteWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.RegisterWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.SubtrackBalanceFromWalletCommand;

import java.util.Optional;

/**
 * Service interface for handling wallet-related commands.
 */
public interface WalletCommandService {

    /**
     * Handle the registration of a new wallet.
     *
     * @param command the command containing wallet registration details
     * @return an Optional containing the registered Wallet, or empty if registration failed
     */
    Optional<Wallet> handle(RegisterWalletCommand command);

    /**
     * Handle adding balance to a wallet.
     *
     * @param command the command containing details for adding balance
     * @return an Optional containing the updated Wallet, or empty if the operation failed
     */
    Optional<Wallet> handle(AddBalanceToWalletCommand command);

    /**
     * Handle subtracting balance from a wallet.
     *
     * @param command the command containing details for subtracting balance
     * @return an Optional containing the updated Wallet, or empty if the operation failed
     */
    Optional<Wallet> handle(SubtrackBalanceFromWalletCommand command);

    /**
     * Handle deleting a wallet.
     *
     * @param command the command containing details for deleting the wallet
     */
    void handle(DeleteWalletCommand command);
}
