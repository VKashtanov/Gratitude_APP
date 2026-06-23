package ru.kashtanov.like_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.like_service.model.LikeEntityType;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface LikeEntityRepo extends JpaRepository<LikeEntityType, Long> {
}
