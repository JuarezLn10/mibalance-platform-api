package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when a wallet deletion operation fails.
 */
public class WalletDeletionFailedException extends RuntimeException {
    public WalletDeletionFailedException(String message) {
        super(message);
    }
}
