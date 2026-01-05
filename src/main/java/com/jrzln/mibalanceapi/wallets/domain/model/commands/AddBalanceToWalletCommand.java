package com.jrzln.mibalanceapi.wallets.domain.model.commands;

/**
 * Command to add balance to a wallet.
 */
public record AddBalanceToWalletCommand(String walletId, double amountToAdd) {}