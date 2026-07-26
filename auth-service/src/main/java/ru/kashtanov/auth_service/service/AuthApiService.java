package ru.kashtanov.auth_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.constants.ErrorMessages;
import ru.kashtanov.auth_service.dto.UserRegisterDto;

import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class AuthApiService {
    private final UserApiClient userApiClient;

    public AuthApiService(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    public Mono<UserRegisterDto> fetchUserByLogin(String username) {
        return userApiClient.fetchByLogin(username);
    }

    public Mono<ResponseEntity<?>> saveUser(UserRegisterDto dto) {
        return userApiClient.register(dto)
                .<ResponseEntity<?>>map(user -> {
                    return ResponseEntity.status(HttpStatus.CREATED).body(user);
                })
                .onErrorResume(RuntimeException.class, e -> {
                    String message = e.getMessage();
                    log.error("Registration error: {}", message);

                    if (message.contains(ErrorMessages.USER_ALREADY_EXISTS) ||
                            message.contains(ErrorMessages.USERNAME_IS_ALREADY_TAKEN)) {
                        return Mono.just(ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(Map.of("error", message)));
                    }

                    if (message.contains(ErrorMessages.VALIDATION_ERROR)
                            || message.contains(ErrorMessages.INVALID_DATA)) {

                        return Mono.just(ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("error", message)));
                    }
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("error", "Internal server error: " + message)));
                });

    }
}
