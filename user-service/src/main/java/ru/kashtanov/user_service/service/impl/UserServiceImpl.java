package ru.kashtanov.user_service.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.UserDeletedResponseDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;
import ru.kashtanov.user_service.exception.ImpossibleSaveUserException;
import ru.kashtanov.user_service.exception.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UserRepo;
import ru.kashtanov.user_service.util.UserUtilService;

import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
//TODO to implement UserService
@Service
public class UserServiceImpl {
    private final UserUtilService utilService = new UserUtilService();
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDtoResponseSaved createUser(RequestUserDto dto) {
        try {
            User user = utilService.transformToUser(dto);
            User savedUser = userRepo.save(user);
            return utilService.transformToRequestUserDto(savedUser);
        } catch (Exception e) {
            throw new ImpossibleSaveUserException("Impossible save user because of an exception: " + e.getMessage());
        }
    }

    public UserDtoResponseDetailed findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        return utilService.transformToResponseDetailedUserDto(user);
    }


    public UserDtoFieldsUpdatedResponse updateUserById(Long id, Map<String, Object> userDetails) throws IllegalArgumentException {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        var updatedFields = utilService.updateUserFields(userDetails, user);
        userRepo.save(user);
        return updatedFields;
    }

    public UserDeletedResponseDto deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        var deletedUser = utilService.transformToUserDeletedResponseDto(user);
        userRepo.deleteById(id);
        return deletedUser;
    }

    public List<UserDtoResponseDetailed> findAllUsers(Pageable pageable) {
        List<User> users = userRepo.findAll(pageable).toList();
        return users.stream().map(utilService::transformToResponseDetailedUserDto).toList();
    }



}
