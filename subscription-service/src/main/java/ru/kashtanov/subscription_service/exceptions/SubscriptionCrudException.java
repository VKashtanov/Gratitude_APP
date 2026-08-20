package ru.kashtanov.subscription_service.exceptions;

/**
 * @author Viktor Кashtanov
 */
public class SubscriptionCrudException extends RuntimeException {
    public SubscriptionCrudException(String message) {
        super(message);
    }
}
