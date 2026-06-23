package ru.kashtanov.like_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.like_service.dto.ErrorResponse;
import ru.kashtanov.like_service.exception.LikeNotFoundException;
import ru.kashtanov.like_service.exception.LikeNotSavedException;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class LikeExceptionHandler {

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLikeNotFoundException(LikeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Impossible to find Like by id" + ex.getMessage())
                        .build());
    }

    @ExceptionHandler(LikeNotSavedException.class)
    public ResponseEntity<ErrorResponse> handleLikeNotSavedException(LikeNotSavedException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error("Impossible to save Like by id" + ex.getMessage())
                        .build());
    }
}
