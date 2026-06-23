package com.group_cordillera.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // <-- IMPORTANTE

@SpringBootApplication
@EnableEurekaServer // <-- ACTIVA EL SERVIDOR CENTRAL
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
