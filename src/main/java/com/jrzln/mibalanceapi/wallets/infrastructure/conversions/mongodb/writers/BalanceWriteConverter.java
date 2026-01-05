package com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.writers;

import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a Balance value object to a Double for MongoDB storage.
 */
public class BalanceWriteConverter implements Converter<Balance, Double> {

    @Override
    public Double convert(Balance source) {
        return source.balance();
    }
}
