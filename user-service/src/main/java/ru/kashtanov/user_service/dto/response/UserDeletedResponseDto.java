package ru.kashtanov.user_service.dto.response;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserDeletedResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
}
