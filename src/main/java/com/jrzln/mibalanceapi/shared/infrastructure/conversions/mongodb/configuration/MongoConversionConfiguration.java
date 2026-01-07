package com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.configuration;

import com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.readers.EmailReadConverter;
import com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.readers.PasswordHashReadConverter;
import com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.writers.EmailWriteConverter;
import com.jrzln.mibalanceapi.iam.infrastructure.conversions.mongodb.writers.PasswordHashWriteConverter;
import com.jrzln.mibalanceapi.profiles.infrastructure.converters.mongodb.readers.RegionNamesWriteConverter;
import com.jrzln.mibalanceapi.profiles.infrastructure.converters.mongodb.writers.RegionNamesReadConverter;
import com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.readers.UserIdReadConverter;
import com.jrzln.mibalanceapi.shared.infrastructure.conversions.mongodb.writers.UserIdWriteConverter;
import com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.readers.BalanceReadConverter;
import com.jrzln.mibalanceapi.wallets.infrastructure.conversions.mongodb.writers.BalanceWriteConverter;
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
                new EmailReadConverter(),

                new UserIdWriteConverter(),
                new UserIdReadConverter(),

                new BalanceWriteConverter(),
                new BalanceReadConverter(),

                new PasswordHashWriteConverter(),
                new PasswordHashReadConverter(),

                new RegionNamesWriteConverter(),
                new RegionNamesReadConverter()
        ));
    }
}
