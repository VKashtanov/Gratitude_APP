package ru.kashtanov.news_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class RedisCrudException extends RuntimeException {
    public RedisCrudException(String message) {
        super(message);
    }
}
