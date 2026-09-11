package ru.kashtanov.comment_service.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.model.Comment;

/**
 * @author Viktor Кashtanov
 */
@Component
public class CommentBuilderService {

    public Comment toComment(@Valid CommentCreateRequest dto) {
        return Comment.builder()
                .userId(dto.getUserId())
                .targetId(dto.getTargetId())
                .targetType(dto.getTargetType())
                .comment(dto.getComment())
                .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .user_id(comment.getUserId())
                .targetId(comment.getTargetId())
                .header(comment.getHeader())
                .description(comment.getDescription())
                .targetType(comment.getTargetType())
                .comment(comment.getComment()).build();
    }
}
