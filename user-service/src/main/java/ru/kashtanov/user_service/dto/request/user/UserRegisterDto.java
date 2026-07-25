package ru.kashtanov.user_service.dto.request.user;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDto(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterDto that = (UserRegisterDto) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }
}