package com.E_Commerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {
	private static final Logger logger = LoggerFactory.getLogger(ECommerceApplication.class);

	public static void main(String[] args) {
		logger.info("Server started on port 8081");
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
