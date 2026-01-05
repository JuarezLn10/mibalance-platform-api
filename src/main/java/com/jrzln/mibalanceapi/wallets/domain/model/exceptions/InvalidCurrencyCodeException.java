package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when an invalid currency code is encountered.
 */
public class InvalidCurrencyCodeException extends RuntimeException {
    public InvalidCurrencyCodeException(String message) {
        super(message);
    }
}
