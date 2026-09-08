package ru.kashtanov.comment_service.exception_handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.comment_service.dto.response.ErrorResponse;
import ru.kashtanov.comment_service.exceptions.DuplicateResourceException;
import ru.kashtanov.comment_service.exceptions.ResourceNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // == VALIDATION EXCEPTIONS ==

    // Is used when body is NULL
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = String.format("HttpMessageNotReadableException occurred: %s", ex.getMessage());
        log.warn(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Request body is required")
                        .error(ex.getMessage())
                        .status(status.value())
                        .build()
        );
    }


    // Is used when upon @NotNull we get NULL in @RequestBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        String errors = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

        String message = String.format("ConstraintViolationException occurred: %s", errors);
        log.warn(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Fields are equal to null or empty")
                        .error(errors)
                        .status(status.value())
                        .build()
        );
    }


    // Is used upon fields validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        String message = String.format("MethodArgumentNotValidException occurred: %s", errors);
        log.warn(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Fields validation failed")
                        .error(errors)
                        .status(status.value())
                        .build()
        );
    }


    // == DB RESOURCES EXCEPTIONS ==

    // DB throwing exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        String message = String.format("DataIntegrityViolationException occurred: %s", ex.getMessage());
        log.error(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Data integrity violation failed")
                        .status(status.value())
                        .build()
        );
    }


    // == OUR CUSTOMIZED RESOURCES EXCEPTIONS ==

    // Resource not found in DB
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        String message = String.format("ResourceNotFoundException occurred: %s", ex.getMessage());
        log.warn(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Resource not found")
                        .error(ex.getMessage())
                        .status(status.value())
                        .build()
        );
    }

    // Found duplicate of resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        String message = String.format("DuplicateResourceException occurred: %s", ex.getMessage());
        log.warn(message);

        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Resource already exists")
                        .error(ex.getMessage())
                        .status(status.value())
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Unexpected error: ", ex);
        return ResponseEntity.status(status).body(
                new ErrorResponse.Builder()
                        .message("Internal server error")
                        .error("An unexpected error occurred")
                        .status(status.value())
                        .build()
        );
    }


}
