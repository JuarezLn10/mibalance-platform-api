package com.jrzln.mibalanceapi.wallets.interfaces.rest.assemblers;

import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses.WalletDetailWrapperResource;

import java.util.List;

/**
 * Assembler class to convert {@link Wallet} entities to {@link WalletDetailWrapperResource}.
 */
public class WalletDetailWrapperResourceFromEntityAssembler {

    /**
     * Converts a list of {@link Wallet} entities to a {@link WalletDetailWrapperResource}.
     *
     * @param wallets the list of Wallet entities
     * @return a WalletDetailWrapperResource containing the wallet details and total count
     */
    public static WalletDetailWrapperResource toResourceFromEntity(List<Wallet> wallets) {

        var walletResources = wallets.stream()
                .map(WalletDetailItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        var userId = wallets.isEmpty() ? null : wallets.getFirst().getUserId();
        assert userId != null : "UserId should not be null when wallets are present";

        return new WalletDetailWrapperResource(
                walletResources,
                walletResources.size(),
                userId.userId()
        );
    }
}
