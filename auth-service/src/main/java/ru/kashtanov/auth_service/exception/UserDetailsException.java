package ru.kashtanov.auth_service.exception;

/**
 * @author Viktor Кashtanov
 */
public class UserDetailsException extends RuntimeException {
    public UserDetailsException(String message) {
        super(message);
    }
}
