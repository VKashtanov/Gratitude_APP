package ru.kashtanov.news_service.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.news_service.dto.ErrorResponse;
import ru.kashtanov.news_service.exceptions.NewsContentException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class NewsContentExceptionHandler {

    @ExceptionHandler(NewsContentException.class)
    public ResponseEntity<ErrorResponse> handleNewsContentException(NewsContentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .error("NewsContent CRUD Exception")
                        .message(ex.getMessage())
                        .status(status.value())
                        .build()
        );
    }
}
