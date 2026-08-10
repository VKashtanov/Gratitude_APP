package ru.kashtanov.news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
public class NewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

}
