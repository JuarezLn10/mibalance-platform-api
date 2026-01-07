package com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.readers;

import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a Decimal128 value from MongoDB into a Balance value object.
 */
public class BalanceReadConverter implements Converter<Decimal128, Balance> {

    @Override
    public Balance convert(Decimal128 source) {
        return new Balance(source.bigDecimalValue());
    }
}
