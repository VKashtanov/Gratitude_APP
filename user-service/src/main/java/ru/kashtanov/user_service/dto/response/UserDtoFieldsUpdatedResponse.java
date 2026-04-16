package ru.kashtanov.user_service.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserDtoFieldsUpdatedResponse {
    private Map<String, Object> updatedFields = new HashMap<>();
}
