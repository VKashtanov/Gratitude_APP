package ru.kashtanov.user_service.dto.request;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class RequestUserDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String portraitUrl;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
}
