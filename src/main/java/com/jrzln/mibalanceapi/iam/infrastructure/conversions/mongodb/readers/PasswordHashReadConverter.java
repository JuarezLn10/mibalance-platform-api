package com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.readers;

import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Converts a String to a PasswordHash value object when reading from MongoDB.
 */
@ReadingConverter
public class PasswordHashReadConverter implements Converter<String, PasswordHash> {

    @Override
    public PasswordHash convert(String source) {
        return new PasswordHash(source);
    }
}
