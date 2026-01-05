package com.jrzln.mibalanceapi.wallets.domain.model.exceptions;

/**
 * Exception thrown when an invalid balance amount is encountered.
 */
public class InvalidBalanceAmountException extends RuntimeException {

    public InvalidBalanceAmountException(String message) {
        super(message);
    }
}
