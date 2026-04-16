package ru.kashtanov.user_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;
import ru.kashtanov.user_service.exception.ImpossibleSaveUserException;
import ru.kashtanov.user_service.exception.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UserRepo;
import ru.kashtanov.user_service.util.UtilWorker;

import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
//TODO to implement UserService
@Service
public class UserServiceImpl {
    private final UtilWorker utilWorker = new UtilWorker();
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDtoResponseSaved createUser(RequestUserDto dto) {
        try {
            User user = utilWorker.transformToUser(dto);
            User savedUser = userRepo.save(user);
            return utilWorker.transformToRequestUserDto(savedUser);
        } catch (Exception e) {
            throw new ImpossibleSaveUserException("Impossible save user because of an exception: " + e.getMessage());
        }
    }

    public UserDtoResponseDetailed findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        return utilWorker.transformToResponseDetailedUserDto(user);
    }

    public UserDtoFieldsUpdatedResponse updateUserById(Long id, Map<String, Object> userDetails) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        var updatedDto = new UserDtoFieldsUpdatedResponse();
        for (Map.Entry<String, Object> entry : userDetails.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "firstname":
                    user.setFirstName((String) value);
                    updatedDto.getUpdatedFields().put("firstname", value);
                    break;
                case "middlename":
                    user.setMiddleName((String) value);
                    updatedDto.getUpdatedFields().put("middlename", value);
                    break;
                case "lastname":
                    user.setLastName((String) value);
                    updatedDto.getUpdatedFields().put("lastname", value);
                    break;
                case "position":
                    user.setPosition((String) value);
                    updatedDto.getUpdatedFields().put("position", value);
                    break;
                case "portrait_url":
                    user.setPortraitUrl((String) value);
                    updatedDto.getUpdatedFields().put("portrait_url", value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    updatedDto.getUpdatedFields().put("email", value);
                    break;
                case "phone":
                    user.setPhone((String) value);
                    updatedDto.getUpdatedFields().put("phone", value);
                    break;
                case "address":
                    user.setAddress((String) value);
                    updatedDto.getUpdatedFields().put("address", value);
                    break;
                case "city":
                    user.setCity((String) value);
                    updatedDto.getUpdatedFields().put("city", value);
                    break;
                case "state":
                    user.setState((String) value);
                    updatedDto.getUpdatedFields().put("state", value);
                    break;
                case "country":
                    user.setCountry((String) value);
                    updatedDto.getUpdatedFields().put("country", value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown user field: " + key);
            }
        }
        userRepo.save(user);
        return updatedDto;
    }


}
