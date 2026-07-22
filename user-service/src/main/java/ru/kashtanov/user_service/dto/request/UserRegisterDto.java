package ru.kashtanov.user_service.dto.request;

import lombok.*;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;

    public UserRegisterDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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