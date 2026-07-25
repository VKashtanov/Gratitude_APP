package ru.kashtanov.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.service.AuthApiService;
import ru.kashtanov.auth_service.service.JwtService;

import java.util.UUID;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthApiService authApiService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthApiService authApiService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authApiService = authApiService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<UserRegisterDto> login(@RequestParam(value = "username") String username) {
        String password = UUID.randomUUID().toString();

        // STEP_10 GETS AND PROCESS REQUEST - put into security wrapper
        // STEP_11 WRAPS IN UsernamePasswordAuthenticationToken
         var authRequest = new UsernamePasswordAuthenticationToken(username, password); // is a container

        // STEP_12 - AuthenticationManager CHECKS_DATA
        Authentication authentication = authenticationManager.authenticate(authRequest);

        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication.getName());

        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);

        Mono<UserRegisterDto> login = authApiService.login(username);
        return ResponseEntity.ok(login.block());
    }

}
