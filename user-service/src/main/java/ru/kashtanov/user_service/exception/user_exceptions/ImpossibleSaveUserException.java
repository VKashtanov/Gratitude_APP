package ru.kashtanov.user_service.exception.user_exceptions;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class ImpossibleSaveUserException extends RuntimeException {

    public ImpossibleSaveUserException(String message) {
        super(message);
    }
}
