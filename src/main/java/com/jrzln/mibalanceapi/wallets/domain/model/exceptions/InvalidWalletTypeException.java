package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when an invalid wallet type is encountered.
 */
public class InvalidWalletTypeException extends RuntimeException {
    public InvalidWalletTypeException(String message) {
        super(message);
    }
}
