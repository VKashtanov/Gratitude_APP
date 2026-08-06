package ru.kashtanov.news_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.news_service.dto.NewsContentDto;
import ru.kashtanov.news_service.service.NewsContentService;

import java.math.BigDecimal;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/news_content")
public class NewsContentController {
    private final NewsContentService newsContentService;

    public NewsContentController(NewsContentService newsContentService) {
        this.newsContentService = newsContentService;
    }

    @PostMapping("/ops1")
    public ResponseEntity<NewsContentDto> loadUpBalance1(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        NewsContentDto dto = newsContentService.updateBalanceViaSqlUpdate(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PostMapping("/ops2")
    public ResponseEntity<NewsContentDto> loadUpBalance2(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        NewsContentDto dto = newsContentService.updateBalanceViaPessimisticLock(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/ops3")
    public ResponseEntity<NewsContentDto> loadUpBalance3(@RequestParam(name = "id") Long id,
                                                         @RequestParam(name = "amount") Float amount) {
        NewsContentDto dto = newsContentService.updateBalanceViaOptimisticLock(id, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
