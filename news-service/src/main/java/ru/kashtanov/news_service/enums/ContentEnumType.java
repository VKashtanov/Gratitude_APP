package ru.kashtanov.news_service.enums;

import lombok.Getter;
import tools.jackson.databind.ser.jdk.JDKKeySerializers;

/**
 * @author Viktor Кashtanov
 */
@Getter
public enum ContentEnumType {
    TEXT(1),
    VIDEO(2),
    IMAGE(3);

    private final int type;

    private ContentEnumType(int type) {
        this.type = type;
    }
}
