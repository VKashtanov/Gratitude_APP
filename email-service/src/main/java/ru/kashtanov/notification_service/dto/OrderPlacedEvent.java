package ru.kashtanov.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
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
    private String orderDate;
}
