package ru.kashtanov.user_service.util;

import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.UserDeletedResponseDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;
import ru.kashtanov.user_service.model.User;

import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
public class UserUtilService {

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

    public UserDtoResponseSaved transformToRequestUserDto(User user) {
        var dto = new UserDtoResponseSaved();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }


    public UserDtoResponseDetailed transformToResponseDetailedUserDto(User user) {
        var dto = new UserDtoResponseDetailed();
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


    public UserDtoFieldsUpdatedResponse updateUserFields(Map<String, Object> userDetails, User user) {
        var updatedFields = new UserDtoFieldsUpdatedResponse();
        for (Map.Entry<String, Object> entry : userDetails.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "firstname":
                    user.setFirstName((String) value);
                    updatedFields.getUpdatedFields().put("firstname", value);
                    break;
                case "middlename":
                    user.setMiddleName((String) value);
                    updatedFields.getUpdatedFields().put("middlename", value);
                    break;
                case "lastname":
                    user.setLastName((String) value);
                    updatedFields.getUpdatedFields().put("lastname", value);
                    break;
                case "position":
                    user.setPosition((String) value);
                    updatedFields.getUpdatedFields().put("position", value);
                    break;
                case "portrait_url":
                    user.setPortraitUrl((String) value);
                    updatedFields.getUpdatedFields().put("portrait_url", value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    updatedFields.getUpdatedFields().put("email", value);
                    break;
                case "phone":
                    user.setPhone((String) value);
                    updatedFields.getUpdatedFields().put("phone", value);
                    break;
                case "address":
                    user.setAddress((String) value);
                    updatedFields.getUpdatedFields().put("address", value);
                    break;
                case "city":
                    user.setCity((String) value);
                    updatedFields.getUpdatedFields().put("city", value);
                    break;
                case "state":
                    user.setState((String) value);
                    updatedFields.getUpdatedFields().put("state", value);
                    break;
                case "country":
                    user.setCountry((String) value);
                    updatedFields.getUpdatedFields().put("country", value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown user field: " + key);
            }
        }
        return updatedFields;
    }

    public UserDeletedResponseDto transformToUserDeletedResponseDto(User user) {
        var dto = new UserDeletedResponseDto();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
