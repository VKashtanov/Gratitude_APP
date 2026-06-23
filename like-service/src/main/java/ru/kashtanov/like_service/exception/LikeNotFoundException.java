package ru.kashtanov.like_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class LikeNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public LikeNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
