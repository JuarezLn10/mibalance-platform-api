package com.jrzln.mibalanceapi.wallets.application.internal.commandservices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.application.acl.ExternalAuthenticationService;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.RegisterWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.WalletSaveFailedException;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.CurrencyCodes;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletNames;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletTypes;
import com.jrzln.mibalanceapi.wallets.infrastructure.persistence.mongodb.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalletCommandServiceImplTest {

    private WalletRepository walletRepository;
    private ExternalAuthenticationService authenticationService;

    private WalletCommandServiceImpl walletCommandService;

    @BeforeEach
    void setUp() {
        walletRepository = mock(WalletRepository.class);
        authenticationService = mock(ExternalAuthenticationService.class);

        walletCommandService = new WalletCommandServiceImpl(walletRepository, authenticationService);
    }

    @Test
    void handleRegisterWallet_shouldThrowException_whenUserIdDoesNotExist() {

        // Arrange
        var type = WalletTypes.SAVINGS;
        var name = WalletNames.BCP;
        var initialBalance = new Balance(100.50);
        var currencyCode = CurrencyCodes.PEN;

        var userId = new UserId("user-123");

        var command = new RegisterWalletCommand(name, type, initialBalance, currencyCode, userId);

        // Act
        when(authenticationService.verifyIfUserExists(userId.userId())).thenReturn(false);

        // Act & Assert
        assertThrows(WalletSaveFailedException.class,
                () -> walletCommandService.handle(command)
        );

        verify(walletRepository, never()).save(any());
    }
}
