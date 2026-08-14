package ru.kashtanov.news_service.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.news_service.dto.ErrorResponse;
import ru.kashtanov.news_service.exceptions.RedisCrudException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class RedisCrudExceptionHandler {

    @ExceptionHandler(RedisCrudException.class)
    public ResponseEntity<ErrorResponse> handleException(RedisCrudException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message("Redis CRUD exception is occurred")
                        .error(ex.getMessage()).build());
    }
}
