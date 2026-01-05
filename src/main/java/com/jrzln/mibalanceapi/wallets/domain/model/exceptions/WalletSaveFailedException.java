package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when saving a wallet fails.
 */
public class WalletSaveFailedException extends RuntimeException {
    public WalletSaveFailedException(String message) {
        super(message);
    }
}
