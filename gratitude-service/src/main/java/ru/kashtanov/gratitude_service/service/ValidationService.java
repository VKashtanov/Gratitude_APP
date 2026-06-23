package ru.kashtanov.gratitude_service.service;

import org.springframework.stereotype.Component;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;

/**
 * @author Viktor Кashtanov
 */
@Component
public class ValidationService {

    public boolean isValid(GratitudeCreateDto dto) {
        return dto.getAuthorId() != null
                && !dto.getRecipientIds().isEmpty()
                && dto.getContent() != null
                && !dto.getContent().isEmpty();
    }

}
