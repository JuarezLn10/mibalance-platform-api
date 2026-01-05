package com.jrzln.mibalanceapi.wallets.domain.model.valueobjects;

import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.InvalidCurrencyCodeException;

import java.util.Arrays;

/**
 * Enumeration of supported currency codes.
 */
public enum CurrencyCodes {
    USD,
    EUR,
    GBP,
    JPY,
    AUD,
    CAD,
    CHF,
    CNY,
    SEK,
    NZD,
    PEN;

    /**
     * Converts a string to its corresponding CurrencyCodes enum constant.
     *
     * @param value the string representation of the currency code
     * @return the corresponding CurrencyCodes enum constant
     */
    public static CurrencyCodes fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidCurrencyCodeException("Currency code cannot be null");
        }

        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidCurrencyCodeException("Invalid currency code: " + value + ". Allowed values are: " + Arrays.toString(values())));
    }
}
