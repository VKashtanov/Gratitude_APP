package ru.kashtanov.gratitude_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GratitudeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GratitudeServiceApplication.class, args);
	}

}
