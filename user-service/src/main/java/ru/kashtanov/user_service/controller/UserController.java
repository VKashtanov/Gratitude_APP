package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;

import ru.kashtanov.user_service.service.impl.UserServiceImpl;

import java.util.Map;

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
    public ResponseEntity<UserDtoResponseSaved> addUser(@RequestBody RequestUserDto dto) {
        System.out.println("Achieved - addUser ");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDtoResponseDetailed> getUserById(@PathVariable Long userId) {
        System.out.println("Achieved -  getUserById ");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDtoFieldsUpdatedResponse> updateById(@PathVariable Long userId,
                                                                   @RequestBody Map<String, Object> map) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId,map));
    }


}
