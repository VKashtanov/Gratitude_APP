package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.ResponseSavedUserDto;

import ru.kashtanov.user_service.service.impl.UserServiceImpl;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseSavedUserDto> addUser(@RequestBody RequestUserDto dto) {
        System.out.println("Achieved adding user");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

}
