package ru.kashtanov.comment_service.exceptions;

/**
 * @author Viktor Кashtanov
 */

public class CommentCrudException extends RuntimeException {
    private String message;

    public CommentCrudException(String message) {
        super(message);
    }
}
