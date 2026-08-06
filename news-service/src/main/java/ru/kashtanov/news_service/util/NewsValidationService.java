package ru.kashtanov.news_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.news_service.dto.NewsDto;

/**
 * @author Viktor Кashtanov
 */
@Component
public class NewsValidationService {

    public static void validateNewsCreation(NewsDto dto) {
        if(dto==null){
            throw new IllegalArgumentException("NewsDto is null");
        }
        if(dto.getAuthorId()==null){
            throw new IllegalArgumentException("AuthorId is null");
        }
        if(dto.getTitle()==null || dto.getTitle().isBlank()){
            throw new IllegalArgumentException("Title is null");
        }
        if(dto.getContent()==null || dto.getContent().isBlank()){
            throw new IllegalArgumentException("Content is null");
        }
    }
}
