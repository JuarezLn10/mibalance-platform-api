package com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.responses;

import java.util.List;

/**
 * Wrapper resource for detailed wallet information.
 *
 * @param wallets List of detailed wallet items
 * @param totalCount Total number of wallets
 * @param userId Identifier of the user associated with the wallets
 *
 * @see WalletDetailItemResource
 */
public record WalletDetailWrapperResource(List<WalletDetailItemResource> wallets, Integer totalCount, String userId) {}