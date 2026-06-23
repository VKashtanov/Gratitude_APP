package ru.kashtanov.gratitude_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class NotValidGratitudeCreateDtoException extends RuntimeException {
    private final HttpStatus status;

    public NotValidGratitudeCreateDtoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
