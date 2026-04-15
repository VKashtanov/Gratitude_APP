package ru.kashtanov.user_service.exception;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
