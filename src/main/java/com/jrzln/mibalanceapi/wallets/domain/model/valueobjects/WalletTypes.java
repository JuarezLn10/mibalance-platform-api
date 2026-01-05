package com.jrzln.mibalanceapi.wallets.domain.model.valueobjects;

import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.InvalidWalletTypeException;

import java.util.Arrays;

/**
 * Enumeration representing different types of wallets.
 */
public enum WalletTypes {
    SAVINGS,
    CHECKING,
    INVESTMENT,
    RETIREMENT;

    /**
     * Converts a string to its corresponding WalletTypes enum constant.
     *
     * @param value the string representation of the wallet type
     * @return the corresponding WalletTypes enum constant
     */
    public static WalletTypes fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidWalletTypeException("Wallet type cannot be null or empty");
        }

        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidWalletTypeException("Invalid wallet type: " + value + ". Allowed values are: " + Arrays.toString(values())));
    }
}
