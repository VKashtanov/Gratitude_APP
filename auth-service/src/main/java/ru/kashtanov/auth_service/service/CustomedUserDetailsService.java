package ru.kashtanov.auth_service.service;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.exception.UserDetailsException;
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
        ValidateService.validateUserName(username);

        Mono<UserRegisterDto> login = authApiService.login(username);
        UserRegisterDto dto = login.block();

        ValidateService.validateUserRegisterDto(dto);
        String username1 = dto.getUsername();
        String password1 = dto.getPassword();
        Set<String> roles = dto.getRoles();

        Set<SimpleGrantedAuthority> setRoles = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet());
        return new User(username1, password1, setRoles);
    }
}
