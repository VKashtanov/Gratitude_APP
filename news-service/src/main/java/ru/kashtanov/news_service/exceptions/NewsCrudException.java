package ru.kashtanov.news_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class NewsCrudException extends RuntimeException {
    public NewsCrudException(String message) {
        super(message);
    }
}
