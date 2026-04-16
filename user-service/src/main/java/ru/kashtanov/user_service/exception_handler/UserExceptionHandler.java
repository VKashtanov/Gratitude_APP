package ru.kashtanov.user_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.user_service.dto.response.ErrorResponse;
import ru.kashtanov.user_service.exception.ImpossibleSaveUserException;
import ru.kashtanov.user_service.exception.UserNotFoundException;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(ImpossibleSaveUserException.class)
    public ResponseEntity<ErrorResponse> impossibleSaveUser(ImpossibleSaveUserException ex) {
        HttpStatus status = HttpStatus.CONFLICT;//409
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error("Impossible save user")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;//404
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error("Not Found")
                        .message(ex.getMessage())
                        .build());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleUserFieldNotFound(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;//400
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error("Bad Request")
                        .message(ex.getMessage())
                        .build());
    }
}
