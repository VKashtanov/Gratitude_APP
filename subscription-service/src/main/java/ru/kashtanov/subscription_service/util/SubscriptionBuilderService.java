package ru.kashtanov.subscription_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.model.Subscription;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Component
public class SubscriptionBuilderService {
    private final ValidationService validationService;

    public SubscriptionBuilderService(ValidationService validationService) {
        this.validationService = validationService;
    }

    public Subscription buildSubscription(SubscriptionDto dto) {
        validationService.validateCommon(dto);

        var subscription = new Subscription();
        subscription.setUserId(dto.getUserId());
        subscription.setTargetId(dto.getTargetId());
        subscription.setType(dto.getType());
        subscription.setStatus(dto.getStatus());
        if (dto.getCreatedAt() == null) {
            dto.setCreatedAt(Instant.now());
        }
        subscription.setCreatedAt(Instant.now());
        subscription.setUpdatedAt(Instant.now());
        subscription.setExpiresAt(dto.getExpiresAt());

        return subscription;
    }
    public SubscriptionDto buildDto(Subscription subscription) {
        var dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUserId());
        dto.setTargetId(subscription.getTargetId());
        dto.setType(subscription.getType());
        dto.setStatus(subscription.getStatus());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());
        dto.setExpiresAt(subscription.getExpiresAt());

        return dto;
    }
}
