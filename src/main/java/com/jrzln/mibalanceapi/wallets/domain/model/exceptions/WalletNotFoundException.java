package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when a wallet is not found.
 */
public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
