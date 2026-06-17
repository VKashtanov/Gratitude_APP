package ru.kashtanov.order_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.OrderSaveDto;
import ru.kashtanov.order_service.model.Order;

/**
 * @author Viktor Кashtanov
 */
@Component
public class OrderMapper {

    public Order toEntity(OrderSaveDto dto) {
        var order = new Order();
        order.setUserId(dto.getUserId());
        order.setProducts(dto.getProducts());
        order.setStatus(dto.getStatus());
        order.setCreatedAt(dto.getCreatedAt());
        order.setFinishedAt(order.getFinishedAt());
        return order;
    }

    public OrderDto toDto(Order order) {
        var dto = new OrderDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setProductIds(order.getProducts());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setFinishedAt(order.getFinishedAt());
        return dto;
    }
}
