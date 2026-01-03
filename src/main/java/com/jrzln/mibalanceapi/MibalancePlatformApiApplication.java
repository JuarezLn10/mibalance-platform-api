package com.jrzln.mibalanceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MibalancePlatformApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MibalancePlatformApiApplication.class, args);
	}

}
