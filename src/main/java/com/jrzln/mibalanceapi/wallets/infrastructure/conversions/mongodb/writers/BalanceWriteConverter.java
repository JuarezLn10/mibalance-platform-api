package com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.writers;

import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a Balance value object to a Decimal128 for MongoDB storage.
 */
public class BalanceWriteConverter implements Converter<Balance, Decimal128> {

    @Override
    public Decimal128 convert(Balance source) {
        return new Decimal128(source.balance());
    }
}
