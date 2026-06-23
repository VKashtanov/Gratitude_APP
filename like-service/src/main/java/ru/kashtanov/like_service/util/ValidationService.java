package ru.kashtanov.like_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.like_service.dto.LikeDto;
import ru.kashtanov.like_service.dto.LikeSaveDto;

/**
 * @author Viktor Кashtanov
 */
@Component
public class ValidationService {

    public boolean isValid(LikeSaveDto dto) {
        return dto.getUserId() != null && dto.getEntityTypeId() != null
                && dto.getTargetId() != null;
    }
}
