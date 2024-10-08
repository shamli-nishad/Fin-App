package com.sha.fin.app.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class FinBudgetServiceLauncher {

	public static void main(String[] args) {
		
		log.info("Started FinBudget Service");
		SpringApplication.run(FinBudgetServiceLauncher.class, args);
	}

}
