package com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletDetailItemResource;

/**
 * Assembler class to convert {@link Wallet} entity to {@link WalletDetailItemResource}.
 */
public class WalletDetailItemResourceFromEntityAssembler {

    /**
     * Static method to convert {@link Wallet} entity to {@link WalletDetailItemResource}.
     *
     * @param entity the Wallet entity to convert
     * @return the corresponding WalletDetailItemResource
     */
    public static WalletDetailItemResource toResourceFromEntity(Wallet entity) {
        return new WalletDetailItemResource(
                entity.getId(),
                entity.getName().name(),
                entity.getType().name(),
                entity.getBalance().balance(),
                entity.getCurrency().name(),
                entity.getCreatedAt()
        );
    }
}
