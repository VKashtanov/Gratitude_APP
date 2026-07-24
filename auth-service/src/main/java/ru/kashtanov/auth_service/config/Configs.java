package ru.kashtanov.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Viktor Кashtanov
 */
@Configuration
public class Configs {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
