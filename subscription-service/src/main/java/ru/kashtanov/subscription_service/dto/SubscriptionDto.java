package ru.kashtanov.subscription_service.dto;

import jakarta.persistence.Column;
import lombok.Data;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumStatus;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumType;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Data
public class SubscriptionDto {
    private Long id;
    private Long userId;
    private Long targetId;
    private SubscriptionEnumType type;
    private SubscriptionEnumStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant expiresAt;
}
