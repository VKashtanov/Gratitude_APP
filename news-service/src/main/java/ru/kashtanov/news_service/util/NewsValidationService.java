package ru.kashtanov.news_service.util;

import org.springframework.stereotype.Component;
import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.exceptions.NewsCrudException;

/**
 * @author Viktor Кashtanov
 */
@Component
public class NewsValidationService {

    public  void validateCommon(NewsDto dto) {
        if(dto==null){
            throw new NewsCrudException("Dto is null");
        }
        if(dto.getAuthorId()==null){
            throw new NewsCrudException("AuthorId is null");
        }
        if(dto.getTitle()==null || dto.getTitle().isBlank()){
            throw new NewsCrudException("Title is invalid: " + dto.getTitle());
        }
        if(dto.getContentIdList()==null){
            throw new NewsCrudException("Content IDs list is null");
        }
    }
}
