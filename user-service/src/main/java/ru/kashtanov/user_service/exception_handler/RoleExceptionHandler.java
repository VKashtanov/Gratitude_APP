package ru.kashtanov.user_service.exception_handler;

/**
 * @author Viktor Кashtanov
 */


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.user_service.dto.response.ErrorResponse;
import ru.kashtanov.user_service.exception.role_exceptions.RoleCrudOperationsException;

import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(RoleCrudOperationsException.class)
    public ResponseEntity<ErrorResponse> handleException(RoleCrudOperationsException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .error("Role Crud Operations Exception")
                        .message(ex.getMessage())
                        .build());
    }
}
