package com.jrzln.mibalanceapi.profiles.infrastructure.converters.mongodb.readers;

import com.jrzln.mibalanceapi.profiles.domain.model.valueobjects.RegionNames;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Converter to transform RegionNames value object to its String representation for MongoDB storage.
 */
@WritingConverter
public class RegionNamesWriteConverter implements Converter<RegionNames, String> {

    @Override
    public String convert(RegionNames source) {
        return source.getValue();
    }
}
