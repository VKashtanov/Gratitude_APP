package ru.kashtanov.user_service.exception.user_exceptions;

/**
 * @author Viktor Кashtanov
 */
public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
