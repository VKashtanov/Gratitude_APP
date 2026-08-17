package ru.kashtanov.news_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kashtanov.news_service.dto.ContentDto;
import ru.kashtanov.news_service.dto.MediaMetaDataDto;
import ru.kashtanov.news_service.service.ContentService;
import ru.kashtanov.news_service.service.MinioService;
import ru.kashtanov.news_service.service.RedisService;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author Viktor Кashtanov
 */

@RestController
@RequestMapping("/api/media")
public class MediaContentController {
    private final MinioService minioService;
    private final RedisService redisService;
    private final ContentService contentService;

    public MediaContentController(MinioService minioService, RedisService redisService, ContentService contentService) {
        this.minioService = minioService;
        this.redisService = redisService;
        this.contentService = contentService;
    }

    // S3 interaction API
    @PostMapping("/content")
    public ResponseEntity<ContentDto> createMediaContent(@RequestPart("file") MultipartFile file,
                                                         @RequestPart("metadata") MediaMetaDataDto dto) {
        ContentDto responseDto = contentService.createContent(dto, file);
        URI uri = URI.create("/api/media/content");
        return ResponseEntity.created(uri).body(responseDto);

    }

    // Redis interaction API
    @GetMapping("/percent")
    public ResponseEntity<String> fetchLoadPercentage(@RequestParam(name = "file_name") String fileName) {
        String string = redisService.fetchPercentageFromRedis(fileName);
        return ResponseEntity.ok().body(string);

    }

    @DeleteMapping("/percent")
    public ResponseEntity<String> deleteLoadPercentage(@RequestParam(name = "file_name") String fileName) {
        redisService.deletePercentageFromRedis(fileName);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/link")
    public ResponseEntity<String> fetchMediaLink(@RequestParam(name = "file_name") String fileName) {
        String linkForContent = minioService.getLinkForContent(fileName);
        return ResponseEntity.ok().body(linkForContent);

    }

}
