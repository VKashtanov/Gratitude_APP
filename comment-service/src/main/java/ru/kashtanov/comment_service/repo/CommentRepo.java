package ru.kashtanov.comment_service.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public abstract interface CommentRepo extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c " +
            "WHERE c.userId=:userId " +
            "AND c.targetId=:targetId " +
            "AND c.targetType=:targetType")
    public Optional<Comment> commentExists(@Param(value = "userId") Long userId,
                                           @Param(value = "targetId") Long targetId,
                                           @Param(value = "targetType") TargetType targetType);


    @Query("SELECT c FROM Comment c " +
            "WHERE c.userId=:userId " +
            "AND (:cursor IS NULL OR :cursor < c.id) " +
            "ORDER BY c.id ASC "+
            "LIMIT :limit"
    )
    public List<Comment> findByUserId(@Param(value = "userId") Long userId,
                                      @Param(value = "cursor") Long cursor,
                                      @Param(value = "limit") Long limit);

    @Query("SELECT c FROM Comment c " +
            "WHERE c.targetId=:targetId " +
            "AND (:cursor IS NULL OR :cursor < c.id) " +
            "ORDER BY c.id ASC "+
            "LIMIT :limit"
    )
    public List<Comment> findByTargetId(@Param(value = "targetId") Long targetId,
                                      @Param(value = "cursor") Long cursor,
                                      @Param(value = "limit") Long limit);


    // PESSIMISTIC_WRITE - add "FOR UPDATE"- read the row, to change it immediately
    // PESSIMISTIC_READ  - add "FOR SHARE" - read the row with warranty that it's not changed while reading


}
