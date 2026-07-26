package ru.kashtanov.user_service.constants;

/**
 * @author Viktor Кashtanov
 */
public class ErrorMessages {
    public static final String USER_ALREADY_EXISTS = "already exists";
    public static final String USERNAME_IS_ALREADY_TAKEN = "username is already taken";
    public static final String EMAIL_ALREADY_IN_USE = "email already in use";
    public static final String FAILED_TO_CREATE_USER = "Failed to create user";


    public static final String VALIDATION_ERROR = "validation";
    public static final String INVALID_DATA = "invalid";

    public static final String USER_NOT_FOUND = "not found";
    public static final String INVALID_CREDENTIALS = "invalid credentials";

    private ErrorMessages() {
    }
}
