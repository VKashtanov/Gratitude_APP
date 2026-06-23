package ru.kashtanov.gratitude_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;
import ru.kashtanov.gratitude_service.dto.GratitudeDto;
import ru.kashtanov.gratitude_service.model.Gratitude;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@Component
public class GratitudeMapperService {

    public GratitudeDto toDto(Gratitude gratitude) {
        var dto = new GratitudeDto();
        dto.setId(gratitude.getId());
        dto.setAuthorId(gratitude.getAuthorId());
        dto.setContent(gratitude.getContent());
        dto.setTimestamp(gratitude.getTimestamp());
        dto.setRecipientIds(gratitude.getRecipientIds());
        return dto;
    }

    public Gratitude toEntity(GratitudeCreateDto dto) {
        var entity = new Gratitude();
        entity.setTimestamp(LocalDateTime.now());
        entity.setAuthorId(dto.getAuthorId());
        entity.setContent(dto.getContent());
        entity.setRecipientIds(dto.getRecipientIds());
        return entity;
    }
}
