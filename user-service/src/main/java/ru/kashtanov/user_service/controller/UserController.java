package ru.kashtanov.user_service.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.RequestUserDto;
import ru.kashtanov.user_service.dto.response.UserDeletedResponseDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;

import ru.kashtanov.user_service.service.impl.UserServiceImpl;

import java.util.List;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDtoResponseDetailed> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDtoFieldsUpdatedResponse> updateById(@PathVariable Long userId, @RequestBody Map<String, Object> map) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId, map));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDeletedResponseDto> deleteById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));
    }

    @GetMapping
    public List<UserDtoResponseDetailed> getAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable);
    }


}
