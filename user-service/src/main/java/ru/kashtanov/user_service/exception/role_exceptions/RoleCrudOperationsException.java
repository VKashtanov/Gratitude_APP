package ru.kashtanov.user_service.exception.role_exceptions;

/**
 * @author Viktor Кashtanov
 */
public class RoleCrudOperationsException extends RuntimeException {
    public RoleCrudOperationsException(String message) {
        super(message);
    }
}
