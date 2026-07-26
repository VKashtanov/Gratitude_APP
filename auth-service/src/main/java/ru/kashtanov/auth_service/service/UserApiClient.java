package ru.kashtanov.auth_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.constants.ErrorMessages;
import ru.kashtanov.auth_service.constants.UriConstant;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.util.ValidateService;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class UserApiClient {
    private final WebClient webClient;

    public UserApiClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<UserRegisterDto> fetchByLogin(String login) {
        ValidateService.validateLogin(login);
        return webClient.get()
                .uri(UriConstant.FETCH_USER_BY_LOGIN + login)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserRegisterDto.class);
    }

    public Mono<UserRegisterDto> register(UserRegisterDto request) {
        ValidateService.validateUserRegisterDto(request, false);
        return webClient.post()
                .uri(UriConstant.POST_REGISTER_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorMessage -> {
                                log.error("Registration error: {}", errorMessage);
                                if (errorMessage.contains(ErrorMessages.USERNAME_IS_ALREADY_TAKEN)) {
                                    return Mono.error(new RuntimeException(ErrorMessages.USERNAME_IS_ALREADY_TAKEN + " " + request.getUsername()));
                                } else if (errorMessage.contains(ErrorMessages.EMAIL_ALREADY_IN_USE)) {
                                    return Mono.error(new RuntimeException(ErrorMessages.EMAIL_ALREADY_IN_USE + " " + request.getEmail()));
                                } else {
                                    return Mono.error(new RuntimeException(ErrorMessages.FAILED_TO_CREATE_USER + " " + errorMessage));
                                }
                            });
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(error -> {
                                log.error(ErrorMessages.SERVER_ERROR + " {}", error);
                                return Mono.error(new RuntimeException(ErrorMessages.SERVER_ERROR + " " + error));
                            });
                })
                .bodyToMono(UserRegisterDto.class); // OK
    }

}
