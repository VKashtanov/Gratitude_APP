package ru.kashtanov.product_service.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kashtanov.product_service.constant.ErrorResponseMsgEnum;
import ru.kashtanov.product_service.dto.response.ErrorResponse;
import ru.kashtanov.product_service.exception.ProductNotFoundException;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(ErrorResponseMsgEnum.PRODUCT_NOT_FOUND.getName())
                        .error(ex.getMessage())
                        .build());
    }
}
