package ru.kashtanov.auth_service.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.util.ValidateService;

/**
 * @author Viktor Кashtanov
 */
@Service
public class AuthApiService {
    private final UserApiClient userApiClient;

    public AuthApiService(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    public Mono<UserRegisterDto> login(String username) {
        ValidateService.validateUserName(username);
        return userApiClient.fetchByLogin(username);
    }
}
