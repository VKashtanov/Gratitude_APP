package ru.kashtanov.user_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.user_service.dto.response.ErrorResponse;
import ru.kashtanov.user_service.exception.user_account_exception.UserAccountException;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class UserAccountExceptionHandler {

    @ExceptionHandler(UserAccountException.class)
    public ResponseEntity<ErrorResponse> handleNewsContentException(UserAccountException ex) {
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
