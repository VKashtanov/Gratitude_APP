package ru.kashtanov.order_service.enums;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum TopicConstant {
    TOPIC("notification_topic");
    private final String value;
    TopicConstant(String value) {
        this.value = value;
    }

}
