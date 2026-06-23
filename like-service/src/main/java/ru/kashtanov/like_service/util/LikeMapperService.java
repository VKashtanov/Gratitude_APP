package ru.kashtanov.like_service.util;

import jdk.jfr.Category;
import org.springframework.stereotype.Component;
import ru.kashtanov.like_service.dto.LikeDto;
import ru.kashtanov.like_service.dto.LikeSaveDto;
import ru.kashtanov.like_service.model.Like;

/**
 * @author Viktor Кashtanov
 */
@Component
public class LikeMapperService {

    public LikeDto toDto(Like like) {
        var dto = new LikeDto();
        dto.setId(like.getId());
//        dto.setEntityType(like.getEntityType());
        dto.setTargetId(like.getTargetId());
        dto.setUserId(like.getUserId());
        dto.setTimestamp(like.getTimestamp());
        return dto;
    }

    public Like toEntity(LikeSaveDto dto) {
        var entity = new Like();
//        entity.setEntityType(dto.getEntityType());
        entity.setTargetId(dto.getTargetId());
        entity.setUserId(dto.getUserId());
        return entity;
    }
}
