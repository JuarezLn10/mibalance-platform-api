package com.jrzln.mibalanceapi.wallets.domain.model.aggregates;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.InvalidBalanceAmountException;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.CurrencyCodes;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletNames;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WalletTest {

    @Test
    void create_shouldCreateWallet_whenParametersAreValid() {
        // Arrange & Act
        var wallet = Wallet.create(
                WalletNames.BCP,
                WalletTypes.SAVINGS,
                new Balance(100.50),
                CurrencyCodes.PEN,
                new UserId("user-123")
        );

        // Assert
        assertNotNull(wallet);
    }

    @Test
    void create_shouldThrowException_whenWalletNameIsNull() {
        // Arrange, Act & Assert
        try {
            Wallet.create(
                    null,
                    WalletTypes.SAVINGS,
                    new Balance(100.50),
                    CurrencyCodes.PEN,
                    new UserId("user-123")
            );
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("Wallet name must not be null"));
        }
    }

    @Test
    void create_shouldThrowException_whenBalanceIsNegative() {
        // Arrange, Act & Assert
        try {
            Wallet.create(
                    WalletNames.BCP,
                    WalletTypes.SAVINGS,
                    new Balance(-50.00),
                    CurrencyCodes.PEN,
                    new UserId("user-123")
            );
        } catch (InvalidBalanceAmountException e) {
            assert(e.getMessage().equals("Balance cannot be negative: -50.0"));
        }
    }

    @Test
    void create_shouldThrowException_whenUserIdIsNull() {
        // Arrange, Act & Assert
        try {
            Wallet.create(
                    WalletNames.BCP,
                    WalletTypes.SAVINGS,
                    new Balance(100.50),
                    CurrencyCodes.PEN,
                    null
            );
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("User ID must not be null"));
        }
    }

    @Test
    void create_shouldThrowException_whenWalletTypeIsNull() {
        // Arrange, Act & Assert
        try {
            Wallet.create(
                    WalletNames.BCP,
                    null,
                    new Balance(100.50),
                    CurrencyCodes.PEN,
                    new UserId("user-123")
            );
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("Wallet type must not be null"));
        }
    }
}