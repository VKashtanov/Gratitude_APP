package ru.kashtanov.comment_service.dto.response;

import lombok.Data;
import ru.kashtanov.comment_service.enums.TargetType;

/**
 * @author Viktor Кashtanov
 */
@Data
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String error;

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.error = builder.error;
    }

    public static class Builder {
        private int status;
        private String message;
        private String error;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

    }


}
