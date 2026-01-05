package com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.domain.model.commands.RegisterWalletCommand;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.CurrencyCodes;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletNames;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletTypes;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.requests.RegisterWalletResource;

/**
 * Assembler class to convert {@link RegisterWalletResource} to {@link RegisterWalletCommand}.
 */
public class RegisterWalletCommandFromResourceAssembler {

    /**
     * Converts a {@link RegisterWalletResource} to a {@link RegisterWalletCommand}.
     *
     * @param targetUserId the ID of the user to whom the wallet will be registered
     * @param resource the RegisterWalletResource to convert
     * @return the corresponding RegisterWalletCommand
     */
    public static RegisterWalletCommand toCommandFromResource(String targetUserId, RegisterWalletResource resource) {
        var name = WalletNames.fromString(resource.name());
        var type = WalletTypes.fromString(resource.type());
        var currencyCode = CurrencyCodes.fromString(resource.currency());
        var initialBalance = new Balance(resource.initialBalance());
        var userId = new UserId(targetUserId);

        return new RegisterWalletCommand(
                name,
                type,
                initialBalance,
                currencyCode,
                userId
        );
    }
}
