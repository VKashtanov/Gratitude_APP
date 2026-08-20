package ru.kashtanov.subscription_service.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.subscription_service.model.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface SubscriptionRepo extends CrudRepository<Subscription, Long> {

    // Mixed with pageable to add to JPQL function of LIMIT and to form SQL QUERY
    @Query("SELECT s FROM Subscription s " +
            "WHERE s.userId = :userId " +
            "AND (:cursor IS NULL OR s.id > :cursor) " +
            "ORDER BY s.id ASC " +
            "LIMIT :limit")
    List<Subscription> findByUserId(
            @Param("userId") Long userId,
            @Param("cursor") Long cursor,
            @Param("limit") int limit
    );


    @Query("SELECT s FROM Subscription  s WHERE s.targetId =: target_id")
    List<Subscription> findByTargetId(@Param("target_id") Long targetId);

}
