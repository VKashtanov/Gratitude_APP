package ru.kashtanov.auth_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.model.JwtToken;
import ru.kashtanov.auth_service.service.JwtService;

import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private UserRegisterDto validRegisterDto;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private  AuthController authController;

    @BeforeEach
    void setUp() {
        validRegisterDto = new UserRegisterDto();
        validRegisterDto.setEmail("test@Email.com");
        validRegisterDto.setPassword("testPassword");
        validRegisterDto.setUsername("testUsername");
        validRegisterDto.setRoles(Set.of("USER"));
    }


    @Test
    void login_ShouldReturnJwtToken_WhenCredentialsAreValid() {
            String username = validRegisterDto.getUsername();

            Authentication mockAuthentication = mock(Authentication.class);
            when(mockAuthentication.getName()).thenReturn(username); // Нужно

            String expectedAccessToken = "mockAccessToken";
            String expectedRefreshToken = "mockRefreshToken";


            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(mockAuthentication);
            when(jwtService.generateAccessToken(any(Authentication.class)))
                    .thenReturn(expectedAccessToken);
            when(jwtService.generateRefreshToken(any(String.class)))
                    .thenReturn(expectedRefreshToken);


            ResponseEntity<JwtToken> response = authController.login(validRegisterDto);
            JwtToken result = response.getBody();


            assertThat(result.getAccessToken()).isEqualTo(expectedAccessToken);
            assertThat(result.getRefreshToken()).isEqualTo(expectedRefreshToken);


            verify(authenticationManager).authenticate(any());
            verify(jwtService).generateAccessToken(any());
            verify(jwtService).generateRefreshToken(eq(username));
        }
}