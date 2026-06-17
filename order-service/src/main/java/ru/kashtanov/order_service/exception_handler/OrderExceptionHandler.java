package ru.kashtanov.order_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.order_service.dto.response.ErrorResponse;
import ru.kashtanov.order_service.exception.OrderNotFoundException;
import ru.kashtanov.order_service.exception.OrderNotSavedException;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotSavedException.class)
    public ResponseEntity<ErrorResponse> handleException(OrderNotSavedException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                        .error("Impossible to save order")
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(OrderNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Impossible to find order")
                        .error(ex.getMessage())
                        .build());
    }

}
