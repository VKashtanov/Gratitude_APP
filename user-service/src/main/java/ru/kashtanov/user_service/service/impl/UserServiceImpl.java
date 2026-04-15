package ru.kashtanov.user_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.ResponseDetailedUserDto;
import ru.kashtanov.user_service.dto.response.ResponseSavedUserDto;
import ru.kashtanov.user_service.exception.ImpossibleSaveUserException;
import ru.kashtanov.user_service.exception.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UserRepo;
import ru.kashtanov.user_service.util.UtilWorker;

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

    public ResponseSavedUserDto createUser(RequestUserDto dto) {
        try {
            User user = utilWorker.transformToUser(dto);
            User savedUser = userRepo.save(user);
            return utilWorker.transformToRequestUserDto(savedUser);
        } catch (Exception e) {
            throw new ImpossibleSaveUserException("Impossible save user because of an exception: " + e.getMessage());
        }
    }

    public ResponseDetailedUserDto findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " is not found"));
        return utilWorker.transformToResponseDetailedUserDto(user);
    }


}
