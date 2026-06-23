package ru.kashtanov.order_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.order_service.constant.EndpointEnum;
import ru.kashtanov.order_service.dto.response.UserDtoResponseDetailed;

/**
 * @author Viktor Кashtanov
 */
@Component
public class UserServiceClient {
    private final WebClient webClient;

    public UserServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserDtoResponseDetailed fetchUserDto(Long userId) {
        // Mono returns one whole object. Flux return several object, kind of sequence
        Mono<UserDtoResponseDetailed> objectMono = webClient.get()
                .uri(EndpointEnum.GET_USER.getUri() + userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() { // to save specified type
                });

        var block = objectMono.block(); // block() makes Sync method
        return block;
    }
}
