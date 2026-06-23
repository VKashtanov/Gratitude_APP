package ru.kashtanov.order_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.order_service.dto.OrderSaveDto;

/**
 * @author Viktor Кashtanov
 */
@Component
public class ValidationService {

    public boolean isValid(OrderSaveDto dto) {
        return dto.getUserId() != null && !dto.getProducts().isEmpty();
    }
}
