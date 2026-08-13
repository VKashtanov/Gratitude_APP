package ru.kashtanov.news_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class MinioS3CustomException extends RuntimeException {
    public MinioS3CustomException(String message) {
        super(message);
    }
}
