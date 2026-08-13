package ru.kashtanov.news_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kashtanov.news_service.service.MinioService;
import ru.kashtanov.news_service.service.RedisService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final MinioService minioService;
    private final RedisService redisService;

    public NewsController(MinioService minioService, RedisService redisService) {
        this.minioService = minioService;
        this.redisService = redisService;
    }

    @PostMapping("/content")
    public ResponseEntity<?> createMedia(@RequestParam("file") MultipartFile file) {
        String string = minioService.addFile(file);
        return ResponseEntity.ok(string);

    }

    @GetMapping("/percent")
    public ResponseEntity<String> fetchLoadPercentage(@RequestParam(name = "file_name") String fileName) {
        String string = redisService.fetchPercentageFromRedis(fileName);
        return ResponseEntity.ok().body(string);

    }

    public void fetchNewsByIds(List<Long> ids) {
    }

    public void fetchNewsPaginated(int pageNumber, int pageSize) {
    }

    public void deleteNews() {
    }

    public void changeNews(Map<String, String> map) {
    }

    @GetMapping("/minio")
    public void doMyCommand() {
    }
}
