package com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.requests;

import java.math.BigDecimal;

/**
 * Resource to register a new wallet.
 *
 * @param name the wallet name
 * @param type the wallet type
 * @param initialBalance the initial balance
 * @param currency the currency code
 */
public record RegisterWalletResource(String name, String type, BigDecimal initialBalance, String currency) {}