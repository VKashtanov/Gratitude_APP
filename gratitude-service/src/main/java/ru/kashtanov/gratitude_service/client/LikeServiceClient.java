package ru.kashtanov.gratitude_service.client;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Viktor Кashtanov
 */
@Component
public class LikeServiceClient {

    private final WebClient webClient;

    public LikeServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void addLike(Long targetId){
        webClient.post()
                .uri("/likes")
                .body(null)
                .retrieve().bodyToMono(String.class);

    }


}
