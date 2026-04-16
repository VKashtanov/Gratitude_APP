package ru.kashtanov.user_service.dto.response;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserDtoResponseSaved {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

}
