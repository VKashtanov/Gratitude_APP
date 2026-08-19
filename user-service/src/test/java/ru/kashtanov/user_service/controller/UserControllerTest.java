package ru.kashtanov.user_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.kashtanov.user_service.exception.user_exceptions.UserNotFoundException;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.service.impl.UserServiceImpl;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Viktor Кashtanov
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    // Test variables
    private Long userId;
    private Long notActualId;
    private User validUser;

    @BeforeEach
    public void setup() {
        userId = 1L;
        notActualId = 2L;

        validUser = new User();
        validUser.setId(1L);
        validUser.setUsername("username");
        validUser.setPassword("password");
        validUser.setEmail("email");

    }

    @Test
    void getUserById_WhenUserExistsInDB() throws Exception {
        when(userService.findUserById(userId)).thenReturn(validUser);

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("email"));

        verify(userService, times(1)).findUserById(userId);
    }

    @Test
    void getUserById_WhenUser_DoesNotExistsInDB() throws Exception {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException("User with id:" + notActualId + " is not found"));

        mockMvc.perform(get("/api/users/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.error").value("Not Found"));

        verify(userService, times(1)).findUserById(anyLong());
    }
}