package ru.kashtanov.comment_service.exception_handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.comment_service.dto.response.ErrorResponse;
import ru.kashtanov.comment_service.exceptions.CommentCrudException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // is used when body is null
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleCommentCrudException(HttpMessageNotReadableException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        System.out.println(ex.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponse.Builder()
                        .message("Request body is required")
                        .error("Missing or malformed JSON body")
                        .status(status.value()).build()
        );
    }

    // is used when @NotNull on @RequestBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                new ErrorResponse.Builder()
                        .message("Validation failed")
                        .error(ex.getMessage())
                        .status(status.value()).build()
        );
    }

    // is used upon fields validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                new ErrorResponse.Builder()
                        .message("Validation failed")
                        .error(errors)
                        .status(status.value()).build()
        );
    }
}
