package ru.kashtanov.order_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class OrderNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public OrderNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
