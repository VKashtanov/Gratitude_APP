package ru.kashtanov.user_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.user_service.dto.response.ErrorResponse;
import ru.kashtanov.user_service.exception.user_roles_exceptions.UserRolesCrudException;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@RestControllerAdvice
public class UserRolesExceptionHandler {

    @ExceptionHandler(UserRolesCrudException.class)
    public ResponseEntity<ErrorResponse> handleUserRolesException(UserRolesCrudException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .message("UserRoles CRUD operation exception")
                        .error(ex.getMessage()).build());
    }
}
