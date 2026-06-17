package ru.kashtanov.product_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ProductNotFoundException(String message, HttpStatus statusCode) {
        super(message);
        this.status = statusCode;
    }
}
