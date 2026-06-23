package ru.kashtanov.like_service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.like_service.model.Like;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface LikeRepo extends CrudRepository<Like, Long> {

}
