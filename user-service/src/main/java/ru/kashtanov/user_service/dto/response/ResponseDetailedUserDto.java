package ru.kashtanov.user_service.dto.response;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class ResponseDetailedUserDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private String portraitUrl;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
}
