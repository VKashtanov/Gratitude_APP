package ru.kashtanov.news_service.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.news_service.model.NewsContent;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface NewsContentRepo extends JpaRepository<NewsContent, Long> {


    @Modifying
    @Query(value = "UPDATE news_content SET balance = balance + :amount WHERE id = :id", nativeQuery = true)
    int addUpBalanceSqlUpdate(@Param(value = "id") Long id, @Param(value = "amount") BigDecimal amount);
    //   @Query("UPDATE NewsContent nc SET nc.balance=nc.balance + :amount WHERE nc.id= :id")


    @Lock(LockModeType.PESSIMISTIC_WRITE) //1. if you use native query add at the end "FOR UPDATE"  = SELECT * FROM news_content WHERE id = :id FOR UPDATE
    //2. JPQL   = SELECT nc FROM NewsContent nc WHERE nc.id= :id   + FOR UPDATE ; it adds under the hood
    @Query("SELECT nc FROM NewsContent nc WHERE nc.id= :id")
    Optional<NewsContent> findByPessimistic(@Param(value = "id") Long id);


}