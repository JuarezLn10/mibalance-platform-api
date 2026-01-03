package com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.configuration;

import com.jrzln.mibalanceapi.auth.infrastructure.conversions.mongodb.readers.EmailReadConverter;
import com.jrzln.mibalanceapi.auth.infrastructure.conversions.mongodb.writers.EmailWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

/**
 * Configuration class to register custom MongoDB conversions.
 */
@Configuration
public class MongoConversionConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new EmailWriteConverter(),
                new EmailReadConverter()
        ));
    }
}
