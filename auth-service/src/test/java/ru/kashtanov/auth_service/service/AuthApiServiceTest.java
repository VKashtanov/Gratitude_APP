package ru.kashtanov.auth_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kashtanov.auth_service.dto.UserRegisterDto;

/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class AuthApiServiceTest {
    @InjectMocks
    private AuthApiService authApiService;
    private UserRegisterDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserRegisterDto("user", "password", "email");
    }

    @Test
    void fetchUserByLogin() {

    }
}