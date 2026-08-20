package ru.kashtanov.subscription_service.exceptions_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.subscription_service.dto.ErrorResponse;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class SubscriptionCrudExceptionHandler {


    @ExceptionHandler(SubscriptionCrudException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionCrudException(SubscriptionCrudException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(
                ErrorResponse.builder()
                        .message("Subscription CRUD exception")
                        .status(httpStatus.value())
                        .error(e.getMessage())
                        .build()
        );
    }
}
