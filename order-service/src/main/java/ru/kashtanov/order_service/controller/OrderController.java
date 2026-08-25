package ru.kashtanov.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.order_service.client.ProductServiceClient;
import ru.kashtanov.order_service.client.UserServiceClient;
import ru.kashtanov.order_service.dto.*;
import ru.kashtanov.order_service.service.OrderProducerService;
import ru.kashtanov.order_service.service.OrderService;

import java.net.URI;
import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderProducerService producerService;
    private final OrderService orderService;


    public OrderController(OrderProducerService producerService, OrderService orderService) {
        this.producerService = producerService;
        this.orderService = orderService;

    }

    //==================== Kafka PRODUCER ========================
    @PostMapping("/events/placed")
    public void placeOrder(@RequestBody OrderPlacedEvent event) {
        if (event.getOrderDate() == null) {
            event.setOrderDate(Instant.now());
            System.out.println("DATA OF ORDER: " + event.getOrderDate());
        }
        producerService.sendOrderPlacedEvent(event);
    }


    // =====================  CRUD API =========================
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderSaveDto dto) {
        OrderDto orderDto = orderService.createOrder(dto);
        URI uri = URI.create("/api/v1/order/" + orderDto.getOrderId());
        return ResponseEntity.created(uri).body(orderDto);
    }


    // ================ BETWEEN MICROSERVICES COMMUNICATION ==================
    @GetMapping("/full/{orderId}")
    public ResponseEntity<?> orderInfoById(@PathVariable Long orderId) {
        FullOrderInfo fullOrderInfo = orderService.provideOrderInfo(orderId);
        return ResponseEntity.ok(fullOrderInfo);
    }


}
