package ru.kashtanov.news_service.dto;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String error;

    public ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.error = builder.error;
    }
    public static Builder builder() {
        return new Builder();
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
