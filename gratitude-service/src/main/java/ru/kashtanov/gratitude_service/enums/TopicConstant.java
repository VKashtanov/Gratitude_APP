package ru.kashtanov.gratitude_service.enums;

import lombok.Getter;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum TopicConstant {
    NOTIFICATION_TOPIC("notification_topic");
    private final String value;
    TopicConstant(String value) {
        this.value = value;
    }

}
