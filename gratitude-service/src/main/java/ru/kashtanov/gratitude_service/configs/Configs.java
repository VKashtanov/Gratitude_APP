package ru.kashtanov.gratitude_service.configs;

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
        // One can also to add some settings additionally if it is really needed
        return WebClient.builder().build();
    }
}
