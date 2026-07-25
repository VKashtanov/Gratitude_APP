package ru.kashtanov.order_service.dto;

import lombok.Data;
import ru.kashtanov.order_service.enums.OrderStatus;


import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
public class OrderDto {
    private Long orderId;
    private Long userId;
    private List<Long> productIds;
    private OrderStatus status;
    private Instant createdAt;
    private Instant finishedAt;
}
