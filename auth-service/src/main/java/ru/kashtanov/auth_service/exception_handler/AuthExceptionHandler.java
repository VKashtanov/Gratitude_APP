package ru.kashtanov.auth_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.auth_service.dto.ErrorResponse;
import ru.kashtanov.auth_service.exception.UserDetailsException;
import ru.kashtanov.auth_service.exception.UserValidationException;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class AuthExceptionHandler {


    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidationException(UserValidationException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(httpStatus.value())
                        .error("Error upon validation")
                        .message(ex.getMessage())
                        .build());
    }
    @ExceptionHandler(UserDetailsException.class)
    public ResponseEntity<ErrorResponse> handleUserDetailsException(UserDetailsException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(httpStatus.value())
                        .error("UserDetails are not valid")
                        .message(ex.getMessage())
                        .build());
    }
}
