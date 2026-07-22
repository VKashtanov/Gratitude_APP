package ru.kashtanov.user_service.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.UserRegisterDto;
import ru.kashtanov.user_service.dto.response.UserDeletedResponseDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;
import ru.kashtanov.user_service.exception.user_exceptions.UserCrudException;
import ru.kashtanov.user_service.exception.user_exceptions.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UserRepo;
import ru.kashtanov.user_service.util.UserUtilService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */

@Service
public class UserServiceImpl {
    private final UserUtilService utilUserService = new UserUtilService();
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(UserRegisterDto dto) {
        try {
            UserUtilService.validateUserRegisterDto(dto);
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user.setEmail(dto.getEmail());
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            String message = e.getRootCause().getMessage();
            if (message.contains("username")) {
                throw new UserCrudException("Username '" + dto.getUsername() + "' is already taken");
            } else if (message.contains("email")) {
                throw new UserCrudException("Email '" + dto.getEmail() + "' is already in use");
            } else {
                throw new UserCrudException("Failed to create user. Please check your data.");
            }
        }
    }

    public User findUserById(Long id) {
        if (id == null) {
            throw new UserNotFoundException("User Id is null");
        }
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
    }


    public UserDtoFieldsUpdatedResponse updateUserById(Long id, Map<String, Object> userDetails) throws IllegalArgumentException {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        var updatedFields = utilUserService.updateUserFields(userDetails, user);
        userRepo.save(user);
        return updatedFields;
    }

    public UserDeletedResponseDto deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        var deletedUser = utilUserService.transformToUserDeletedResponseDto(user);
        userRepo.deleteById(id);
        return deletedUser;
    }

    public List<UserDtoResponseDetailed> findAllUsers(Pageable pageable) {
        List<User> users = userRepo.findAll(pageable).toList();
        return users.stream().map(
                UserUtilService::transformToResponseDetailedUserDto
        ).toList();
    }


}
