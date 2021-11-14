package com.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieNetContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieNetContentServiceApplication.class, args);
	}

}
