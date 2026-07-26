package ru.kashtanov.auth_service.util;

import ru.kashtanov.auth_service.dto.UserRegisterDto;
import ru.kashtanov.auth_service.exception.UserValidationException;

/**
 * @author Viktor Кashtanov
 */
public class ValidateService {

    public static void validateLogin(String username) {
        if (username == null || username.isBlank()) {
            throw new UserValidationException("Username is empty or null: " + username);
        }
    }

    public static void validateUserRegisterDto(UserRegisterDto dto, boolean checksRole) {
        if (dto == null) {
            throw new UserValidationException("UserRegisterDto is null");
        }
        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
            throw new UserValidationException("Username is not valid: " + dto.getUsername());
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new UserValidationException("Password is not valid: " + dto.getPassword());
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new UserValidationException("Email is not valid: " + dto.getEmail());
        }
        if (checksRole) {
            if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
                throw new UserValidationException("Roles is not valid" + dto.getRoles());
            }
        }

    }
}
