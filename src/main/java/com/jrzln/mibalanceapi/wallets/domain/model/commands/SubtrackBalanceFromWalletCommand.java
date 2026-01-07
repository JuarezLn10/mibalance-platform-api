package com.jrzln.mibalanceapi.wallets.domain.model.commands;

import java.math.BigDecimal;

/**
 * Command to subtract a specified amount from a wallet's balance.
 */
public record SubtrackBalanceFromWalletCommand(String walletId, BigDecimal amountToSubtrack) {}