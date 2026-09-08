package ru.kashtanov.comment_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.comment_service.dto.response.ErrorResponse;
import ru.kashtanov.comment_service.exceptions.CommentCrudException;
import ru.kashtanov.comment_service.exceptions.CommentValidationException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(CommentCrudException.class)
    public ResponseEntity<ErrorResponse> handleCommentCrudException(CommentCrudException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                new ErrorResponse.Builder()
                        .message(ex.getMessage())
                        .error("CRUD Exception")
                        .status(status.value()).build()
        );

    }

    @ExceptionHandler(CommentValidationException.class)
    public ResponseEntity<ErrorResponse> handleCommentValidationException(CommentValidationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.badRequest().body(
                new ErrorResponse.Builder()
                        .message(ex.getMessage())
                        .error("Validation Exception")
                        .status(status.value()).build()
        );

    }
}
