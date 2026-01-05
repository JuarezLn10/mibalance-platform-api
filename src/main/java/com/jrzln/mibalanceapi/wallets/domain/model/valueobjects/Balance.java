package com.jrzln.mibalanceapi.wallets.domain.model.valueobjects;

import com.jrzln.mibalanceapi.wallets.domain.model.exceptions.InvalidBalanceAmountException;
import jakarta.validation.constraints.NotNull;

/**
 * Value object representing a wallet balance.
 *
 * @param balance the balance amount, must be non-negative
 */
public record Balance(
        @NotNull(message = "Balance must not be null")
        Double balance
) {

    // Constructor validation to ensure balance is non-negative
    public Balance {
        if (balance < 0) {
            throw new InvalidBalanceAmountException("Balance cannot be negative: " + balance);
        }
    }

    /**
     * Method to add an amount to the balance.
     *
     * @param amount the amount to add
     * @return a new Balance instance with the updated balance
     */
    public Balance add(Double amount) {
        return new Balance(this.balance + amount);
    }

    /**
     * Method to subtract an amount from the balance.
     *
     * @param amount the amount to subtract
     * @return a new Balance instance with the updated balance
     */
    public Balance subtract(Double amount) {
        var newBalance = this.balance - amount;
        if (newBalance < 0) {
            throw new InvalidBalanceAmountException("Insufficient balance for subtraction: " + amount);
        }
        return new Balance(newBalance);
    }
}
