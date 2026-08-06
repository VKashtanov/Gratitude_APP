package ru.kashtanov.news_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.service.TransactionTestService;

import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final TransactionTestService testService;

    public NewsController(TransactionTestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto dto) {
        testService.testExecution();
        return ResponseEntity.ok(dto);
    }

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
