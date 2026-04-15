package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.ResponseDetailedUserDto;
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
        System.out.println("Achieved - addUser ");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDetailedUserDto> getUserById(@PathVariable Long userId) {
        System.out.println("Achieved -  getUserById ");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
    }


}
