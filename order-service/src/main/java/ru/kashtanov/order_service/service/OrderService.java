package ru.kashtanov.order_service.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.OrderSaveDto;
import ru.kashtanov.order_service.dto.ProductDto;
import ru.kashtanov.order_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.order_service.exception.OrderNotFoundException;
import ru.kashtanov.order_service.exception.OrderNotSavedException;
import ru.kashtanov.order_service.model.Order;
import ru.kashtanov.order_service.repo.OrderRepo;
import ru.kashtanov.order_service.util.OrderMapper;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.Instant.from;

/**
 * @author Viktor Кashtanov
 */
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;
    private final ValidationService validationService;
    private final WebClient webClient;

    public OrderService(OrderMapper orderMapper, OrderRepo orderRepo, ValidationService validationService, WebClient webClient) {
        this.orderMapper = orderMapper;
        this.orderRepo = orderRepo;
        this.validationService = validationService;
        this.webClient = webClient;
    }

    // =============  CRUD ===================
    public OrderDto createOrder(OrderSaveDto dto) {
        boolean isValid = validationService.isValid(dto);

        if (isValid) {
            Order order = orderMapper.toEntity(dto);
            order.setCreatedAt(LocalDateTime.now());
            Order save = orderRepo.save(order);
            return orderMapper.toDto(save);
        } else {
            throw new OrderNotSavedException("Not saved", HttpStatus.BAD_REQUEST);
        }
    }

    public List<OrderDto> getAllOrders() {
        List<Order> all = orderRepo.findAll();
        return all.stream()
                .map(orderMapper::toDto)
                .toList();

    }

    public Optional<OrderDto> getOrderById(Long id) {
        Optional<Order> foundProduct = orderRepo.findById(id);
        return foundProduct.map(orderMapper::toDto);
    }

    public void deleteOrderById(int id) {
    }

    @Transactional
    public OrderDto deleteProductById(Long id) {
        Order orderNotFound = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found", HttpStatus.NOT_FOUND));
        orderRepo.delete(orderNotFound);
        return orderMapper.toDto(orderNotFound);
    }

//    public void updateOrder(OrderSaveDto dto) {
//    }




}
