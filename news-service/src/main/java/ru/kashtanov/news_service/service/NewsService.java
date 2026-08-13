package ru.kashtanov.news_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.exceptions.NewsCrudException;
import ru.kashtanov.news_service.model.Content;
import ru.kashtanov.news_service.model.News;
import ru.kashtanov.news_service.repo.NewsRepo;
import ru.kashtanov.news_service.util.NewsValidationService;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class NewsService {
    private final NewsRepo newsRepo;
    private final NewsValidationService validationService;
    private final ContentService contentService;

    public NewsService(NewsRepo newsRepo, NewsValidationService validationService, ContentService contentService) {
        this.newsRepo = newsRepo;
        this.validationService = validationService;
        this.contentService = contentService;
    }

    @Transactional
    public NewsDto createNews(@RequestBody NewsDto dto) {
        validationService.validateCommon(dto);
        var news = new News();
        news.setTitle(dto.getTitle());
        news.setAuthor(dto.getAuthorId());

        List<Content> contents = contentService.fetchContentByIds(dto.getContentIdList());

        return null;
    }

    public List<Long> fetchContentByIds(List<Long> idList) {
   return null;
    }

    public News findNewsById(Long id) {
        if (id == null) {
            throw new NewsCrudException("Can't find news since ID is null");
        }
        return newsRepo.findById(id).orElseThrow(() -> new NewsCrudException("News ID not found"));

    }
}
