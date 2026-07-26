package ru.kashtanov.auth_service.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.exception.UserValidationException;
import ru.kashtanov.auth_service.util.ValidateService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */

@Service
public class CustomedUserDetailsService implements UserDetailsService {

    private final AuthApiService authApiService;

    public CustomedUserDetailsService(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserRegisterDto dto = null;
        try {
            Mono<UserRegisterDto> login = authApiService.fetchUserByLogin(username);
            dto = login.block();

            ValidateService.validateUserRegisterDto(dto,true);
        } catch (UserValidationException ex) {
            throw new BadCredentialsException("Bad credentials: " + ex.getMessage());
        }
        String login = dto.getUsername();
        String password = dto.getPassword();
        Set<String> roles = dto.getRoles();

        Set<SimpleGrantedAuthority> setRoles = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet());
        return new User(login, password, setRoles);
    }
}
