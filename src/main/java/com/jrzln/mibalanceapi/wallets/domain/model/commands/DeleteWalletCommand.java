package com.jrzln.mibalanceapi.wallets.domain.model.commands;

/**
 * Command to delete a wallet by its ID.
 */
public record DeleteWalletCommand(String walletId) {}