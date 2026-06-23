package ru.kashtanov.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.order_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.order_service.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullOrderInfo {
    private Long orderId;
    private List<Long> productIds;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private UserDtoResponseDetailed user;
    private List<ProductDto> products;
}
