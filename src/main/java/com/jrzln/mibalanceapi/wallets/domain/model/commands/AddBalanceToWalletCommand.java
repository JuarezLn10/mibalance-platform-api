package com.jrzln.mibalanceapi.wallets.domain.model.commands;

import java.math.BigDecimal;

/**
 * Command to add balance to a wallet.
 */
public record AddBalanceToWalletCommand(String walletId, BigDecimal amountToAdd) {}