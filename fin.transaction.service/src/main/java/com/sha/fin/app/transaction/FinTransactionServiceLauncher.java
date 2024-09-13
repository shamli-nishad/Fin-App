package com.sha.fin.app.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
@EnableFeignClients
public class FinTransactionServiceLauncher {

	public static void main(String[] args) {
		log.info("Launching the FinTransaction Service");
		SpringApplication.run(FinTransactionServiceLauncher.class, args);
	}

}
