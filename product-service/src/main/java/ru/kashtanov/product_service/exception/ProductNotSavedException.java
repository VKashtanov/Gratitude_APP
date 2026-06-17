package ru.kashtanov.product_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Viktor Кashtanov
 */
@Getter
public class ProductNotSavedException extends RuntimeException {
    private final HttpStatus status;

    public ProductNotSavedException(String message, HttpStatus status) {
        super();
        this.status = status;
    }
}
