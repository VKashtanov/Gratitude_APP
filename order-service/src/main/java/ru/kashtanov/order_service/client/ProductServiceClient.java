package ru.kashtanov.order_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
@Component
public class ProductServiceClient {
    private final WebClient webClient;

    public ProductServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ProductDto> getProductsByAPI(OrderDto dto) {
        String ids = dto.getProductIds().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Mono<List<ProductDto>> objectMono = webClient.get()
                .uri("http://localhost:9015/api/v1/products/pointed?ids=" + ids)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<ProductDto> block = objectMono.block();
        return block;
    }
}
