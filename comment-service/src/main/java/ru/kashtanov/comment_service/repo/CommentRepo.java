package ru.kashtanov.comment_service.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.model.Comment;

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
}
