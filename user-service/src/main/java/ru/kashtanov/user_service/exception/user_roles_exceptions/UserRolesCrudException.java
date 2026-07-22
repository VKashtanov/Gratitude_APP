package ru.kashtanov.user_service.exception.user_roles_exceptions;

/**
 * @author Viktor Кashtanov
 */
public class UserRolesCrudException extends RuntimeException {
    public UserRolesCrudException(String message) {
        super(message);
    }
}
