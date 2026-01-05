package com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.writers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a UserId value object to a String when writing to MongoDB.
 */
public class UserIdWriteConverter implements Converter<UserId, String> {

    @Override
    public String convert(UserId source) {
        return source.userId();
    }
}
