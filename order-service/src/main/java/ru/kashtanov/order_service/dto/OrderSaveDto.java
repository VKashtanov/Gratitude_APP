package ru.kashtanov.order_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.order_service.enums.OrderStatus;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderSaveDto {
    private Long userId;
    private List<Long> products;
    private OrderStatus status;
    private Instant createdAt;
    private Instant finishedAt;
}
