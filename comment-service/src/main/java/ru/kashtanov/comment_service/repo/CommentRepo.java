package ru.kashtanov.comment_service.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.comment_service.model.Comment;

/**
 * @author Viktor Кashtanov
 */
@Repository
public abstract interface CommentRepo extends CrudRepository<Comment, Long> {
}
