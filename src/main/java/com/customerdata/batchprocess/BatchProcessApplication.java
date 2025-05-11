package com.customerdata.batchprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcessApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(BatchProcessApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BatchProcessApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Application is running fine!");
	}
}
