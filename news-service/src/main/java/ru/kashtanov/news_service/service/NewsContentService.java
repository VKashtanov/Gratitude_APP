package ru.kashtanov.news_service.service;

import jakarta.persistence.OptimisticLockException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.news_service.dto.NewsContentDto;
import ru.kashtanov.news_service.dto.NewsDto;
import ru.kashtanov.news_service.exceptions.NewsContentException;
import ru.kashtanov.news_service.model.NewsContent;
import ru.kashtanov.news_service.repo.NewsContentRepo;
import ru.kashtanov.news_service.util.NewsContentValidationService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Viktor Кashtanov
 */
@Service
public class NewsContentService {
    private final NewsContentRepo newsContentRepo;
    private final NewsContentValidationService validationService;

    public NewsContentService(NewsContentRepo newsContentRepo, NewsContentValidationService validationService) {
        this.newsContentRepo = newsContentRepo;
        this.validationService = validationService;
    }

    // 1. Update via SQL_UPDATE straight usage. Instant, 1 operation
    @Transactional
    public NewsContentDto updateBalanceViaSqlUpdate(Long id, Float sum) {
        // to update
        BigDecimal amount = BigDecimal.valueOf(sum);
        newsContentRepo.addUpBalanceSqlUpdate(id, amount);
        // to see
        NewsContent nc = newsContentRepo.findById(id).get();
        // to show
        return NewsContentDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    @Transactional
    public NewsContentDto updateBalanceViaPessimisticLock(Long id, Float sum) {
        // to select
        BigDecimal amount = BigDecimal.valueOf(sum);
        NewsContent nc = newsContentRepo.findByPessimistic(id).orElseThrow(() -> new NewsContentException("Pessimistic locked by NewsContent"));
        // to process and update
        BigDecimal updatedBalance = nc.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        nc.setBalance(updatedBalance);
        newsContentRepo.save(nc);
        // to show
        return NewsContentDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    @Retryable(value = {OptimisticLockException.class},
            maxRetries = 3L,
            delay = 100
    )
    @Transactional
    public NewsContentDto updateBalanceViaOptimisticLock(Long id, Float sum) {
        // to select
        BigDecimal amount = BigDecimal.valueOf(sum);
        NewsContent nc = newsContentRepo.findByPessimistic(id).orElseThrow(() -> new NewsContentException("Pessimistic locked by NewsContent"));
        // to process and update
        BigDecimal updatedBalance = nc.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        nc.setBalance(updatedBalance);
        newsContentRepo.save(nc);
        // to show
        return NewsContentDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }


//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
//    public NewsContentDto create(NewsContentDto dto) throws InterruptedException {
//        validationService.validate(dto);
//        Long id = dto.getId();
//
//        Supplier<NewsContentException> supplier = () -> new NewsContentException("No value exists");
//        NewsContent nc = newsContentRepo.findById(id).orElseThrow(supplier);
//
//
//        BigDecimal add = nc.getBalance().add(BigDecimal.valueOf(100));
//        nc.setBalance(add);
//        newsContentRepo.save(nc);
//        Thread.sleep(3000);
//
//        return new NewsContentDto(nc.getId(), nc.getType(), nc.getAdditional(), nc.getBalance());
//
//
//    }


}
