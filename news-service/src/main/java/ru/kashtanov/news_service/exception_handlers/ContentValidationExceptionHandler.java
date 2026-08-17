package ru.kashtanov.news_service.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.news_service.dto.ContentDto;
import ru.kashtanov.news_service.dto.ErrorResponse;
import ru.kashtanov.news_service.exceptions.ContentValidationException;
import ru.kashtanov.news_service.exceptions.MinioS3CustomException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class ContentValidationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleContentValidationException(ContentValidationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message("Content Validation Exception happened")
                        .error(ex.getMessage()).build());
    }
}
