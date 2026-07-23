package ru.kashtanov.user_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kashtanov.user_service.dto.request.user.UserRegisterDto;
import ru.kashtanov.user_service.exception.user_exceptions.UserCrudException;
import ru.kashtanov.user_service.exception.user_exceptions.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UserRepo;

import java.util.Optional;


/**
 * @author Viktor Кashtanov
 */

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRegisterDto testDto;
    private UserRegisterDto notValidDto;

    @BeforeEach
    void setUp() {
        var name = "john";
        var email = "john@test.com";
        var pass = "password123";
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername(name);
        testUser.setEmail(email);
        testUser.setPassword(pass);

        notValidDto = new UserRegisterDto("", email, pass);
        testDto = new UserRegisterDto(name, email, pass);
    }

    @Test
    void createUser_WhenUserData_NotValid() {
        assertThrows(UserCrudException.class, () -> {
            userService.createUser(notValidDto);
        });
    }

    @Test
    void createUser_WhenUserDataValid() {
        when(userRepo.save(any(User.class))).thenReturn(testUser);
        User user = userService.createUser(testDto);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(testDto.getUsername());
        assertThat(user.getEmail()).isEqualTo(testDto.getEmail());
        assertThat(user.getPassword()).isEqualTo(testDto.getPassword());
        assertThat(user.getId()).isEqualTo(testUser.getId());
        verify(userRepo, times(1)).save(any(User.class));
    }


    @Test
    void findUserById_WhenUserIsInDB() {
        when(userRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        User userById = userService.findUserById(testUser.getId());
        assertThat(userById).isNotNull();
        assertThat(userById.getId()).isEqualTo(testUser.getId());
        verify(userRepo, times(1)).findById(testUser.getId());
    }

    @Test
    void findUserById_WhenUserIsNotInDB() {
        Long userId = testUser.getId();
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(userId);
        });
        verify(userRepo, times(1)).findById(testUser.getId());
    }

    @Test
    void updateUserById() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void findAllUsers() {
    }
}