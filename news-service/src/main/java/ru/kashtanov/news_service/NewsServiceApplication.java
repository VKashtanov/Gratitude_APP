package ru.kashtanov.news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import ru.kashtanov.news_service.dto.NewsContentDto;
import ru.kashtanov.news_service.dto.NewsDto;


@SpringBootApplication
@EnableRetry
public class NewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

}
