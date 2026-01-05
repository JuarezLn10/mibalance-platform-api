package com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.readers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a String to a UserId value object when reading from MongoDB.
 */
public class UserIdReadConverter implements Converter<String, UserId> {

    @Override
    public UserId convert(String source) {
        return new UserId(source);
    }
}
