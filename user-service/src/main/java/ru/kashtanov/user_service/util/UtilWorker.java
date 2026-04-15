package ru.kashtanov.user_service.util;

import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.ResponseDetailedUserDto;
import ru.kashtanov.user_service.dto.response.ResponseSavedUserDto;
import ru.kashtanov.user_service.model.User;

/**
 * @author Viktor Кashtanov
 */
public class UtilWorker {

    // ** UTIL USER CLASS METHODS **
    public User transformToUser(RequestUserDto dto) {
        var user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setPosition(dto.getPosition());
        user.setPortraitUrl(dto.getPortraitUrl());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setCountry(dto.getCountry());
        return user;
    }

    public ResponseSavedUserDto transformToRequestUserDto(User user) {
        var dto = new ResponseSavedUserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }


    //ResponseDetailedUserDto

    public ResponseDetailedUserDto transformToResponseDetailedUserDto(User user) {
        var dto = new ResponseDetailedUserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMiddleName(user.getMiddleName());
        dto.setPosition(user.getPosition());
        dto.setPortraitUrl(user.getPortraitUrl());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setCountry(user.getCountry());
        return dto;
    }
}
