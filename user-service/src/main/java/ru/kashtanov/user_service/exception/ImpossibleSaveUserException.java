package ru.kashtanov.user_service.exception;

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
