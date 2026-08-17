package ru.kashtanov.news_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class ContentValidationException extends RuntimeException {
    public ContentValidationException(String message) {
        super(message);
    }
}
