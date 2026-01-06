package com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletResource;

/**
 * Assembler class to convert {@link Wallet} entity to {@link WalletResource}.
 */
public class WalletResourceFromEntityAssembler {

    /**
     * Static method to convert {@link Wallet} entity to {@link WalletResource}.
     *
     * @param entity the Wallet entity to convert
     * @return the corresponding WalletResource
     */
    public static WalletResource toResourceFromEntity(Wallet entity) {
        return new WalletResource(
                entity.getId(),
                entity.getName().name(),
                entity.getType().name(),
                entity.getBalance().balance(),
                entity.getInitialBalance().balance(),
                entity.getCurrency().name(),
                entity.getCreatedAt(),
                entity.getUserId().userId()
        );
    }
}
