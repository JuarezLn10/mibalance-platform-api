package com.jrzln.mibalanceapi.profiles.infrastructure.converters.mongodb.writers;

import com.jrzln.mibalanceapi.profiles.domain.model.valueobjects.RegionNames;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Converter to transform String values from MongoDB into RegionNames value objects.
 */
@ReadingConverter
public class RegionNamesReadConverter implements Converter<String, RegionNames> {

    @Override
    public RegionNames convert(String source) {
        return RegionNames.fromString(source);
    }
}
