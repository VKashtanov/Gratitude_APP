package ru.kashtanov.news_service.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.kashtanov.news_service.model.Content;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
public abstract interface ContentRepo extends CrudRepository<Content, Long> {

    // SELECT * FROM  content c WHERE c.id IN :ids ;
    @Query("SELECT c FROM Content c WHERE c.id IN :ids")
    public abstract List<Content> findByIds(@Param(value = "ids") List<Long> ids);
}
