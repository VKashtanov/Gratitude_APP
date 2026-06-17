package ru.kashtanov.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.order_service.client.ProductServiceClient;
import ru.kashtanov.order_service.client.UserServiceClient;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.OrderPlacedEvent;
import ru.kashtanov.order_service.dto.OrderSaveDto;
import ru.kashtanov.order_service.dto.ProductDto;
import ru.kashtanov.order_service.service.OrderProducerService;
import ru.kashtanov.order_service.service.OrderService;

import java.net.URI;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderProducerService producerService;
    private final OrderService orderService;
    private final ProductServiceClient productService;
    private final UserServiceClient userService;

    public OrderController(OrderProducerService producerService, OrderService orderService, ProductServiceClient productService, UserServiceClient userService) {
        this.producerService = producerService;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/events/placed")
    public void placeOrder(@RequestBody OrderPlacedEvent event) {
        System.out.println("Created IN");
        producerService.sendOrderPlacedEvent(event);
        System.out.println("Created OUT");
    }

    // =====================  CRUD API =========================
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderSaveDto dto) {
        System.out.println("Created IN");
        OrderDto orderDto = orderService.createOrder(dto);
        URI uri = URI.create("/api/v1/order/" + orderDto.getOrderId());
        return ResponseEntity.created(uri).body(orderDto);
    }

    // ================ BETWEEN MICROSERVICES COMMUNICATION ==================
    @PostMapping("/full")
    public ResponseEntity<?> orderInfoById(@RequestBody OrderDto dto) {
        System.out.println("Created IN");
        List<ProductDto> productsByAPI = productService.getProductsByAPI(dto);
        var user = userService.fetchUserDto(dto);
        return ResponseEntity.ok(productsByAPI);
    }


}
