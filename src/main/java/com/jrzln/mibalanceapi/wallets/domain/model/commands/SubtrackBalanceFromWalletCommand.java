package com.jrzln.mibalanceapi.wallets.domain.model.commands;

/**
 * Command to subtract a specified amount from a wallet's balance.
 */
public record SubtrackBalanceFromWalletCommand(String walletId, double amountToSubtrack) {}