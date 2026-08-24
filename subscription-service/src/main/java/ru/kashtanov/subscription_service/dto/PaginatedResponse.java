package ru.kashtanov.subscription_service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Data
public class PaginatedResponse {
    private Long nextCursor;
    private boolean hasMore;
    private List<SubscriptionDto> subscriptions;
}
