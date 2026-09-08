package ru.kashtanov.comment_service.enums;

import ru.kashtanov.comment_service.util.TargetTypeDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Viktor Кashtanov
 */
@JsonDeserialize(using = TargetTypeDeserializer.class)
public enum TargetType {
    GRATITUDE,
    NEWS,
    COMMENT
}
