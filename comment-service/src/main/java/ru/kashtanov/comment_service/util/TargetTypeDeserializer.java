package ru.kashtanov.comment_service.util;


import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import ru.kashtanov.comment_service.enums.TargetType;


import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Viktor Кashtanov
 */
public class TargetTypeDeserializer extends ValueDeserializer<TargetType> {

    @Override
    public TargetType deserialize(JsonParser p, DeserializationContext context) {
        String value = p.getText();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return TargetType.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            String validValues = Arrays.stream(TargetType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(
                    String.format("Invalid target type: '%s'. Accepted values: %s", value, validValues)
            );
        }
    }
}