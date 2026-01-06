package com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.writers;

import com.jrzln.mibalanceapi.iam.domain.model.valueobjects.PasswordHash;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Converts a PasswordHash value object to a String when writing to MongoDB.
 */
@WritingConverter
public class PasswordHashWriteConverter implements Converter<PasswordHash, String> {

    @Override
    public String convert(PasswordHash source) {
        return source.value();
    }
}
