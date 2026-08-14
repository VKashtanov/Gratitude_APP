package ru.kashtanov.news_service.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.news_service.dto.ErrorResponse;
import ru.kashtanov.news_service.exceptions.MinioS3CustomException;
import ru.kashtanov.news_service.exceptions.NewsCrudException;

/**
 * @author Viktor Кashtanov
 */
@RestController
public class MinioS3CustomExceptionHandler {

    @ExceptionHandler(MinioS3CustomException.class)
    public ResponseEntity<ErrorResponse> handleMinioS3CustomException(MinioS3CustomException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message("MinioS3 exception is occurred")
                        .error(e.getMessage()).build());
    }
}
