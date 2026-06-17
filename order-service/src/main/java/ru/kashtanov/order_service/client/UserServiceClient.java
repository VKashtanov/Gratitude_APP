package ru.kashtanov.order_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.order_service.dto.OrderDto;
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

    public UserDtoResponseDetailed fetchUserDto(OrderDto dto) {
        var userId = dto.getUserId();
        // Mono returns one whole object. Flux return several object, kind of sequence
        Mono<UserDtoResponseDetailed> objectMono = webClient.get()
                .uri("http://localhost:9060/api/users/" + userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() { // to save specified type
                });

        var block = objectMono.block(); // block() makes Sync method
        return block;
    }
}
