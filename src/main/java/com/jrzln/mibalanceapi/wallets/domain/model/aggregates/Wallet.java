package com.jrzln.mibalanceapi.wallets.domain.model.aggregates;

import com.jrzln.mibalanceapi.shared.domain.model.aggregates.AuditableDocument;
import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.CurrencyCodes;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletNames;
import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.WalletTypes;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Aggregate root representing a Wallet entity.
 */
@Getter
@Document(collection = "wallets")
public class Wallet extends AuditableDocument {

    // Wallet name (e.g., BCP, GOOGLE_WALLET, YAPE)
    @Field("name")
    private WalletNames name;

    // Wallet type (e.g., SAVINGS, CHECKING)
    @Field("type")
    private WalletTypes type;

    // Wallet balance as a value object
    @Valid
    @Field("balance")
    private Balance balance;

    // Initial balance when the wallet is registered as a value object
    @Valid
    @Field("initialBalance")
    private Balance initialBalance;

    // Currency code for the wallet (e.g., USD, EUR)
    @Field("currency")
    private CurrencyCodes currency;

    // Associated user ID as a value object
    @Valid
    @Field("userId")
    private UserId userId;

    // Protected no-argument constructor for framework use
    protected Wallet() {}

    // Constructor to initialize all fields
    private Wallet(WalletNames name, WalletTypes type, Balance initialBalance, CurrencyCodes currency, UserId userId) {
        this.name = name;
        this.type = type;
        this.balance = initialBalance;
        this.initialBalance = initialBalance; // Set initial balance to the starting balance
        this.currency = currency;
        this.userId = userId;
    }

    // Static factory method to create a new Wallet instance
    public static Wallet create(WalletNames name, WalletTypes type, Balance initialBalance, CurrencyCodes currency, UserId userId) {
        return new Wallet(name, type, initialBalance, currency, userId);
    }

    /**
     * Method to add an amount to the wallet balance.
     *
     * @param amount the amount to add
     *
     * @see Balance#add(Double)
     */
    public void addBalance(Double amount) {
        this.balance = this.balance.add(amount);
    }

    /**
     * Method to subtract an amount from the wallet balance.
     *
     * @param amount the amount to subtract
     *
     * @see Balance#subtract(Double)
     */
    public void subtractBalance(Double amount) {
        this.balance = this.balance.subtract(amount);
    }
}