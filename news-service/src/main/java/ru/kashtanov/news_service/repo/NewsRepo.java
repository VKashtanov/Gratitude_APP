package ru.kashtanov.news_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.news_service.model.News;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
}
