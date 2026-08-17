package ru.kashtanov.news_service.repo;

import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("DELETE FROM Content c WHERE c.storedFileName = :filename")
    void deleteByStoredFilename(@Param(value = "filename") String filename);
}
