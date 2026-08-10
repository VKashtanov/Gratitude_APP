package ru.kashtanov.news_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.dto.UserAccountDto;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class TransactionTestService {

    private final UserAccountService userAccountService;


    public TransactionTestService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void testExecution(Long id, Float amount) {
        log.info("Start: " + Instant.now());
        CompletableFuture<UserAccountDto> nf1 = startTransaction(id, 1F);
        CompletableFuture<UserAccountDto> nf2 = startTransaction(id, 100F);
        log.info("End: " + Instant.now());
        Void join = CompletableFuture.allOf(nf1, nf2).join();
        log.info("join: " + join + " TIME: " + Instant.now());
    }

    private CompletableFuture<UserAccountDto> startTransaction(Long id, Float amount) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Starting transaction amount: "+ amount+" , " + Instant.now());
            return userAccountService.test1(id, amount);
        });
    }
}
