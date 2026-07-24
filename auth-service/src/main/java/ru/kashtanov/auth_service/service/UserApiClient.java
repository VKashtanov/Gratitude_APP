package ru.kashtanov.auth_service.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;

import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
@Service
public class UserApiClient {
    private final WebClient webClient;

    public UserApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<UserRegisterDto> fetchByLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        return webClient.get()
                .uri("http://localhost:9060/api/users?username=" + login)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserRegisterDto.class);
    }

}
