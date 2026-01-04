package com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.readers;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Converts a String to an Email value object when reading from MongoDB.
 */
@ReadingConverter
public class EmailReadConverter implements Converter<String, Email> {

    @Override
    public Email convert(String source) {
        return new Email(source);
    }
}
