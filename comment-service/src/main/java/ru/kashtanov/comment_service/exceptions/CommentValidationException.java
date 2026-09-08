package ru.kashtanov.comment_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class CommentValidationException extends RuntimeException {
    public CommentValidationException(String message) {
        super(message);
    }
}
