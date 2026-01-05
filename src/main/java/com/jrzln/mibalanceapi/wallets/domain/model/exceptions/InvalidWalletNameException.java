package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when a wallet name is invalid.
 */
public class InvalidWalletNameException extends RuntimeException {
    public InvalidWalletNameException(String message) {
        super(message);
    }
}
