package ru.kashtanov.news_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.news_service.dto.NewsContentDto;
import ru.kashtanov.news_service.exceptions.NewsContentException;

/**
 * @author Viktor Кashtanov
 */
@Component
public class NewsContentValidationService {

    public  void validate(NewsContentDto dto) {
        if(dto==null) throw new NewsContentException("DTO is null");
        if(dto.getAdditional()==null || dto.getAdditional().isBlank()) throw new NewsContentException("Additional is null or blank");
        if(dto.getType()==null) throw new NewsContentException("Type is null");

    }
}
