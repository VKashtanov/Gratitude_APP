package ru.kashtanov.order_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class OrderNotSavedException extends RuntimeException {
    private final HttpStatus status;

    public OrderNotSavedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
