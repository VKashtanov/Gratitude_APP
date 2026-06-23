package ru.kashtanov.gratitude_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.gratitude_service.dto.ErrorResponse;
import ru.kashtanov.gratitude_service.exception.NotValidGratitudeCreateDtoException;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class GratitudeExceptionHandler {

    @ExceptionHandler(NotValidGratitudeCreateDtoException.class)
    public ResponseEntity<ErrorResponse> handleNotValidGratitudeCreateDtoException(NotValidGratitudeCreateDtoException ex) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error("Dto is not valid")
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage()).build()
        );

    }

}
