package ru.kashtanov.comment_service.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.exceptions.CommentCrudException;
import ru.kashtanov.comment_service.model.Comment;

/**
 * @author Viktor Кashtanov
 */
@Component
public class CommentBuilderService {

    public Comment toComment(@Valid CommentCreateRequest dto) {

        var comment = new Comment().
        comment.setTargetId(dto.getTargetId());
        comment.setComment(dto.getComment());
        comment.setTargetType(dto.getTargetType());
        comment.setUser_id(dto.getUserId());
        return comment;
    }
}
