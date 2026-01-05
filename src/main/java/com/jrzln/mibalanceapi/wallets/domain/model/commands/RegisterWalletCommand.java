package com.jrzln.mibalanceapi.wallets.domain.model.commands;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.CurrencyCodes;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletNames;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletTypes;

/**
 * Command to register a new wallet.
 */
public record RegisterWalletCommand(WalletNames name, WalletTypes type, Balance balance, CurrencyCodes currency, UserId userId) {}