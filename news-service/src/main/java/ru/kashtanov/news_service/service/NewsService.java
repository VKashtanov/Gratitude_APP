package ru.kashtanov.news_service.service;

import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.repo.NewsRepo;

/**
 * @author Viktor Кashtanov
 */
@Service
public class NewsService {

    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }
}
