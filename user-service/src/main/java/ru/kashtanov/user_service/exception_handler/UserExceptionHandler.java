package ru.kashtanov.user_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.user_service.dto.response.ErrorResponse;
import ru.kashtanov.user_service.exception.ImpossibleSaveUserException;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(ImpossibleSaveUserException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(ImpossibleSaveUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage()).build());
    }
}
