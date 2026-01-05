package com.jrzln.mibalanceapi.wallets.domain.model.valueobjects;

import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.InvalidWalletNameException;

import java.util.Arrays;

/**
 * Enum representing the names of various digital wallets.
 * Each constant corresponds to a specific wallet service.
 */
public enum WalletNames {
    YAPE,
    PLIN,
    TUNKI,
    LUKITA,
    AGORA,
    BIM,
    GOOGLE_WALLET,
    BCP,
    BBVA,
    SCOTIABANK,
    INTERBANK,
    BANCO_DE_LA_NACION,
    BANCO_PICHINCHA,
    COMPARTAMOS,
    BANCO_FALABELLA,
    OTHER;

    /**
     * Converts a string to its corresponding WalletNames enum constant.
     *
     * @param value the string representation of the wallet name
     * @return the corresponding WalletNames enum constant
     */
    public static WalletNames fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidWalletNameException("Wallet name cannot be null");
        }

        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidWalletNameException("Invalid wallet name: " + value + ". Allowed values are: " + Arrays.toString(values())));
    }
}