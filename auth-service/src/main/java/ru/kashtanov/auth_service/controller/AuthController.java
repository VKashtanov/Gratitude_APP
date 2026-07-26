package ru.kashtanov.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.model.JwtToken;
import ru.kashtanov.auth_service.service.AuthApiService;
import ru.kashtanov.auth_service.service.JwtService;

import java.util.Map;


/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthApiService authApiService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(AuthApiService authApiService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authApiService = authApiService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    // STEP_10 GETS AND PROCESS REQUEST - put into security wrapper
    // STEP_11 WRAPS IN UsernamePasswordAuthenticationToken
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody UserRegisterDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        var authRequest = new UsernamePasswordAuthenticationToken(username, password); // is a container
        Authentication authentication = null;

        // STEP_12 - AuthenticationManager CHECKS_DATA
        authentication = authenticationManager.authenticate(authRequest);

        String accessToken = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication.getName());

        return ResponseEntity.ok(new JwtToken(accessToken, refreshToken));
    }

    // todo make registration service
    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(@RequestBody UserRegisterDto dto) {
        return authApiService.saveUser(dto);
    }
}


