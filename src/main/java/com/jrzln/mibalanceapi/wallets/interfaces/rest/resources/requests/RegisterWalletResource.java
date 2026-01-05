package com.jrzln.mibalanceapi.wallets.interfaces.rest.resources.requests;

/**
 * Resource to register a new wallet.
 *
 * @param name the wallet name
 * @param type the wallet type
 * @param initialBalance the initial balance
 * @param currency the currency code
 * @param userId the user ID associated with the wallet
 */
public record RegisterWalletResource(String name, String type, Double initialBalance, String currency, String userId) {}