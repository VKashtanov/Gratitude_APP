package ru.kashtanov.news_service.service;

import jakarta.persistence.OptimisticLockException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.news_service.dto.UserAccountDto;
import ru.kashtanov.news_service.exceptions.NewsContentException;
import ru.kashtanov.news_service.model.UserAccount;
import ru.kashtanov.news_service.repo.UserAccountRepo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class UserAccountService {
    private final UserAccountRepo userAccountRepo;

    public UserAccountService(UserAccountRepo userAccountRepo) {
        this.userAccountRepo = userAccountRepo;

    }

    //0. Wrong way
    //isolation =  Isolation.READ_COMMITTED - upon 2 parallel trn, it is not warranted, that transaction will be completed
    //isolation =  Isolation.READ_UNCOMMITTED - upon 2 parallel trn, it is not warranted, that transaction will be completed
    @Transactional
    public UserAccountDto test1(Long id, Float sum) {
        BigDecimal amount = BigDecimal.valueOf(sum);
        Optional<UserAccount> byId = userAccountRepo.findByPessimistic(id);
        var nc = byId.get();
        // to process and update
        BigDecimal updatedBalance = nc.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        nc.setBalance(updatedBalance);
        userAccountRepo.save(nc);
        // to show
        return UserAccountDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    // 1. Update via SQL_UPDATE straight usage. Instant, 1 operation
    @Transactional
    public UserAccountDto updateBalanceViaSqlUpdate(Long id, Float sum) {
        // to update
        BigDecimal amount = BigDecimal.valueOf(sum);
        userAccountRepo.addUpBalanceSqlUpdate(id, amount);
        // to see
        UserAccount nc = userAccountRepo.findById(id).get();
        // to show
        return UserAccountDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    @Transactional
    public UserAccountDto updateBalanceViaPessimisticLock(Long id, Float sum) {
        // to select
        BigDecimal amount = BigDecimal.valueOf(sum);
        UserAccount nc = userAccountRepo.findByPessimistic(id).orElseThrow(() -> new NewsContentException("Pessimistic locked by NewsContent"));
        // to process and update
        BigDecimal updatedBalance = nc.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        nc.setBalance(updatedBalance);
        userAccountRepo.save(nc);
        // to show
        return UserAccountDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    @Retryable(value = {OptimisticLockException.class},
            maxRetries = 3L,
            delay = 100
    )
    @Transactional
    public UserAccountDto updateBalanceViaOptimisticLock(Long id, Float sum) {
        // to select
        BigDecimal amount = BigDecimal.valueOf(sum);
        UserAccount nc = userAccountRepo.findByPessimistic(id).orElseThrow(() -> new NewsContentException("Pessimistic locked by NewsContent"));
        // to process and update
        BigDecimal updatedBalance = nc.getBalance().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        nc.setBalance(updatedBalance);
        userAccountRepo.save(nc);
        // to show
        return UserAccountDto.builder()
                .balance(nc.getBalance())
                .additional(nc.getAdditional())
                .type(nc.getType()).build();
    }

    private void timeout(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
