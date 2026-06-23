package com.group_cordillera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
@EnableDiscoveryClient
@SpringBootApplication
public class GroupCordilleraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupCordilleraApplication.class, args);
	}
	@Bean
	//@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}
