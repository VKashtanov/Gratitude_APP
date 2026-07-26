package ru.kashtanov.user_service.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.user.UserRegisterDto;
import ru.kashtanov.user_service.dto.response.UserDeletedResponseDto;
import ru.kashtanov.user_service.dto.response.UserDtoFieldsUpdatedResponse;
import ru.kashtanov.user_service.dto.response.UserDtoResponseDetailed;
import ru.kashtanov.user_service.dto.response.UserDtoResponseSaved;

import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.service.impl.UserServiceImpl;
import ru.kashtanov.user_service.util.UserUtilService;

import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserRegisterDto> createUser(@RequestBody UserRegisterDto dto) {
        User user = userService.createUser(dto);
        var response = UserUtilService.toUserDtoResponseSaved(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDtoResponseDetailed> getUserById(@PathVariable Long userId) {
        User userById = userService.findUserById(userId);
        var response = UserUtilService.toUserDtoResponseDetailed(userById);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<UserRegisterDto> getUserByUserName(@RequestParam(value = "username") String username) {
        User user = userService.findUserByUsername(username);
        var response = UserUtilService.toUserRegisterDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<UserDtoFieldsUpdatedResponse> updateById(@PathVariable Long userId, @RequestBody Map<String, Object> map) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId, map));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDeletedResponseDto> deleteById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(userId));
    }

//    @GetMapping
//    public List<UserDtoResponseDetailed> getAllUsers(Pageable pageable) {
//        return userService.findAllUsers(pageable);
//    }


}
