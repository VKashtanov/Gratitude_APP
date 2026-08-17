package ru.kashtanov.news_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.news_service.dto.ContentDto;
import ru.kashtanov.news_service.exceptions.ContentValidationException;

/**
 * @author Viktor Кashtanov
 */

@Component
public class ContentValidationService {

    public boolean validateCommon(ContentDto dto) {
        if (dto == null) {
            throw new ContentValidationException("DTO is null");
        }
        if(dto.getCreatorId()==null){
            throw new ContentValidationException("CreatorId is null");
        }
        if(dto.getOriginalFileName()==null){
            throw new ContentValidationException("OriginalFileName is null");
        }
        return true;
    }


}
