package ru.kashtanov.news_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class NewsContentException extends RuntimeException {
    public NewsContentException(String message) {
        super(message);
    }
}
