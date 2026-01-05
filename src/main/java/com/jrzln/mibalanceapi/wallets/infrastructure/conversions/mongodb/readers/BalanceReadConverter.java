package com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.readers;

import com.jrzln.mibalanceapi.wallets.domain.model.valueobjects.Balance;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a Double value from MongoDB into a Balance value object.
 */
public class BalanceReadConverter implements Converter<Double, Balance> {

    @Override
    public Balance convert(Double source) {
        return new Balance(source);
    }
}
