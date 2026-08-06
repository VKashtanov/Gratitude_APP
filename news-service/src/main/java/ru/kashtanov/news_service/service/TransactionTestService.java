package ru.kashtanov.news_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.dto.NewsContentDto;
import ru.kashtanov.news_service.enums.NewsContentEnum;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class TransactionTestService {

    private final NewsContentService newsContentService;


    public TransactionTestService(NewsContentService newsContentService) {
        this.newsContentService = newsContentService;
    }

    public void testExecution() {
        NewsContentDto dto1 = new NewsContentDto.Builder().type(NewsContentEnum.TEXT).additional("dto1").build();
        NewsContentDto dto2 = new NewsContentDto.Builder().type(NewsContentEnum.TEXT).additional("dto2").build();

        log.info("Start: " + Instant.now());
//        CompletableFuture<NewsContentDto> nf1 = startTransaction(dto1);
//        CompletableFuture<NewsContentDto> nf2 = startTransaction(dto2);
        log.info("End: " + Instant.now());
//        Void join = CompletableFuture.allOf(nf1, nf2).join();
    }

//    public CompletableFuture<NewsContentDto> startTransaction(NewsContentDto dto) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                return newsContentService.create(dto);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
}
