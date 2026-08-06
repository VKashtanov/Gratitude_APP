package ru.kashtanov.news_service.util;

import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.model.News;

/**
 * @author Viktor Кashtanov
 */
public class NewsUtilService {

    public static News toNews(NewsDto dto) {
        News news = new News();
        news.setId(dto.getId());
        news.setAuthor(dto.getAuthorId());
        news.setTitle(dto.getTitle());
        return null;
    }


}
