package ru.kashtanov.news_service.enums;

import lombok.Getter;
import tools.jackson.databind.ser.jdk.JDKKeySerializers;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum ContentEnumType {
    TEXT(0),
    VIDEO(1),
    IMAGE(2),
    AUDIO(3),
    OTHER(4);

    private final int type;

    private ContentEnumType(int type) {
        this.type = type;
    }
}
