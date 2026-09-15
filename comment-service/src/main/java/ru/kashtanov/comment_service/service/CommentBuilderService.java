package ru.kashtanov.comment_service.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.model.Comment;

/**
 * @author Viktor Кashtanov
 */
@Component
@Validated
public class CommentBuilderService {

    public Comment toComment(@Valid @NotNull CommentCreateRequest dto) {
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
                .userId(comment.getUserId())
                .targetId(comment.getTargetId())
                .header(comment.getHeader())
                .description(comment.getDescription())
                .targetType(comment.getTargetType())
                .comment(comment.getComment()).build();
    }
}
