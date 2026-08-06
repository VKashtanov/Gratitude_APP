package ru.kashtanov.news_service.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.news_service.dto.ErrorResponse;
import ru.kashtanov.news_service.exceptions.NewsCrudException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class NewsExceptionHandler {

    @ExceptionHandler(NewsCrudException.class)
    public ResponseEntity<ErrorResponse> handleNewsCrudException(NewsCrudException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.ok().body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message("CRUD operation exception is occurred")
                        .error(e.getMessage()).build());
    }
}
