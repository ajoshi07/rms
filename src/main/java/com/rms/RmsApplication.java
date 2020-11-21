package com.rms;

import com.rms.controller.RateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class RmsApplication {

	static Logger logger = LoggerFactory.getLogger(RmsApplication.class);
	public static void main(String[] args) {
		logger.info("App started");
		SpringApplication.run(RmsApplication.class, args);
	}

}
