package ru.kashtanov.auth_service.exception;

/**
 * @author Viktor Кashtanov
 */
public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
