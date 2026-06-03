package ru.kashtanov.order_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.order_service.dto.OrderPlacedEvent;
import ru.kashtanov.order_service.service.OrderProducerService;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
   private final OrderProducerService producerService;

    public OrderController(OrderProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/events/placed")
    public void createOrder(@RequestBody OrderPlacedEvent event){
        System.out.println("Created IN");
        producerService.sendOrderPlacedEvent(event);
        System.out.println("Created OUT");
    }
}
