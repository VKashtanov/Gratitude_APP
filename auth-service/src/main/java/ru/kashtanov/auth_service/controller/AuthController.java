package ru.kashtanov.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.service.AuthApiService;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthApiService authApiService;

    public AuthController(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    @PostMapping
    public ResponseEntity<UserRegisterDto> login(@RequestParam(value = "username") String username) {
        Mono<UserRegisterDto> login = authApiService.login(username);
        return ResponseEntity.ok(login.block());
    }

}
