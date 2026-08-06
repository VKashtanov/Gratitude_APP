package ru.kashtanov.news_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.exceptions.NewsCrudException;
import ru.kashtanov.news_service.model.News;
import ru.kashtanov.news_service.repo.NewsRepo;
import ru.kashtanov.news_service.util.NewsValidationService;

/**
 * @author Viktor Кashtanov
 */
@Service
public class NewsService {
    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public NewsDto createNews(@RequestBody NewsDto dto) {
        NewsValidationService.validateNewsCreation(dto);
        return null;
    }

    public News findNewsById(Long id) {
        if (id == null) {
            throw new NewsCrudException("Can't find news since ID is null");
        }
        return newsRepo.findById(id).orElseThrow(() -> new NewsCrudException("News ID not found"));

    }
}
