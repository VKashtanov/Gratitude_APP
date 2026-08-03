package ru.kashtanov.order_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */

// 3. You have to prepare the class you gonna send to Kafka
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private String userEmail;
    private Double orderPrice;
    private Instant orderDate;
}
