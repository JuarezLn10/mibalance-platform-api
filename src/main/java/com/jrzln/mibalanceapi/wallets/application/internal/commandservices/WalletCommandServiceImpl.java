package com.jrzln.mibalanceapi.wallets.application.internal.commandservices;

import com.jrzln.mibalanceapi.wallets.application.acl.ExternalAuthenticationService;
import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.AddBalanceToWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.DeleteWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.RegisterWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.SubtrackBalanceFromWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.WalletNotFoundException;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.WalletSaveFailedException;
import com.jrzln.mibalanceapi.wallets.domain.services.WalletCommandService;
import com.jrzln.mibalanceapi.wallets.infrastructure.persistence.mongodb.repositories.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link WalletCommandService} interface.
 * This service handles commands related to wallet operations such as registration,
 */
@Service
public class WalletCommandServiceImpl implements WalletCommandService {

    private final Logger LOGGER = LoggerFactory.getLogger(WalletCommandServiceImpl.class);

    private final WalletRepository walletRepository;

    private final ExternalAuthenticationService authenticationService;

    // Constructor.
    public WalletCommandServiceImpl(WalletRepository walletRepository, ExternalAuthenticationService authenticationService) {
        this.walletRepository = walletRepository;
        this.authenticationService = authenticationService;
    }

    /**
     * Handle the registration of a new wallet.
     *
     * @param command the command containing wallet registration details
     * @return an Optional containing the registered Wallet, or empty if registration failed
     */
    @Override
    public Optional<Wallet> handle(RegisterWalletCommand command) {

        if (!authenticationService.verifyIfUserExists(command.userId().userId())) {
            throw new WalletSaveFailedException("Cannot register wallet. User with ID " + command.userId().userId() + " does not exist.");
        }

        Wallet wallet;

        try {
            wallet = Wallet.create(command);
            walletRepository.save(wallet);
        } catch (DataAccessException ex) {
            LOGGER.error("Failed to register wallet for user with ID {}: {}", command.userId(), ex.getMessage());
            throw new WalletSaveFailedException("Failed to register wallet for user with ID " + command.userId());
        }

        return Optional.of(wallet);
    }

    /**
     * Handle adding balance to a wallet.
     *
     * @param command the command containing details for adding balance
     * @return an Optional containing the updated Wallet, or empty if the operation failed
     */
    @Override
    public Optional<Wallet> handle(AddBalanceToWalletCommand command) {
        try {
            verifyIfWalletExists(command.walletId());

            walletRepository.findById(command.walletId()).map(wallet -> {
                wallet.addBalance(command.amountToAdd());
                walletRepository.save(wallet);
                return wallet;
            });
        } catch (Exception ex) {
            LOGGER.error("Failed to add balance to wallet with ID {}: {}", command.walletId(), ex.getMessage());
            throw new WalletSaveFailedException("Failed to add balance to wallet with ID " + command.walletId());
        }

        return Optional.empty();
    }

    /**
     * Handle subtracting balance from a wallet.
     *
     * @param command the command containing details for subtracting balance
     * @return an Optional containing the updated Wallet, or empty if the operation failed
     */
    @Override
    public Optional<Wallet> handle(SubtrackBalanceFromWalletCommand command) {
        try {
            verifyIfWalletExists(command.walletId());

            walletRepository.findById(command.walletId()).map(wallet -> {
                wallet.subtractBalance(command.amountToSubtrack());
                walletRepository.save(wallet);
                return wallet;
            });
        }
         catch (Exception ex) {
            LOGGER.error("Failed to subtract balance from wallet with ID {}: {}", command.walletId(), ex.getMessage());
            throw ex;
        }

        return Optional.empty();
    }

    /**
     * Handle deleting a wallet.
     *
     * @param command the command containing details for deleting the wallet
     */
    @Override
    public void handle(DeleteWalletCommand command) {
        try {
            verifyIfWalletExists(command.walletId());
            walletRepository.deleteById(command.walletId());
        } catch (Exception ex) {
            LOGGER.error("Failed to delete wallet with ID {}: {}", command.walletId(), ex.getMessage());
            throw new WalletSaveFailedException("Failed to delete wallet with ID " + command.walletId());
        }
    }

    /**
     * Verify if a wallet exists by its ID.
     *
     * @param walletId the ID of the wallet to verify
     */
    private void verifyIfWalletExists(String walletId) {
        if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException("Wallet not found for ID " + walletId);
        }
    }
}
