package com.project.planb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanbApplication.class, args);
	}

}
