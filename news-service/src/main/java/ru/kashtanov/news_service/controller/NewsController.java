package ru.kashtanov.news_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */

@RestController
@RequestMapping("/api/news")
public class NewsController {


    public void fetchNewsById(Long id) {
    }

    public void fetchNewsByIds(List<Long> ids) {
    }

    public void fetchNewsPaginated(int pageNumber, int pageSize) {
    }

    public void deleteNews() {
    }

    public void changeNews(Map<String, String> map) {
    }
}
