package com.jrzln.mibalanceapi.auth.infrastructure.conversions.mongodb.writers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Converts an Email value object to its String representation for MongoDB storage.
 */
@WritingConverter
public class EmailWriteConverter implements Converter<Email, String> {

    @Override
    public String convert(Email source) {
        return source.email();
    }
}
