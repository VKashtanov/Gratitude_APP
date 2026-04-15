package ru.kashtanov.user_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@Data
public class ResponseSavedUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

}
