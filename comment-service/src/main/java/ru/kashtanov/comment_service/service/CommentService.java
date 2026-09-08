package ru.kashtanov.comment_service.service;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.request.CommentDto;
import ru.kashtanov.comment_service.exceptions.CommentCrudException;

import ru.kashtanov.comment_service.exceptions.DuplicateResourceException;
import ru.kashtanov.comment_service.model.Comment;
import ru.kashtanov.comment_service.repo.CommentRepo;

import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
public class CommentService {
    private final CommentBuilderService builderService;
    private final CommentRepo commentRepo;

    public CommentService(CommentBuilderService builderService, CommentRepo commentRepo) {
        this.builderService = builderService;
        this.commentRepo = commentRepo;
    }

    @Transactional
    public CommentDto createComment(CommentCreateRequest dto) {
        Comment comment = builderService.toComment(dto);
        Optional<Comment> commentExists = commentRepo.commentExists(dto.getUserId(), dto.getTargetId(), dto.getTargetType());

        if (commentExists.isPresent()) {
            String message = String.format("Comment with userId: %d, tagetId: %d, target type: %s - already exists",
                    comment.getUserId(), comment.getTargetId(), comment.getTargetType());
            throw new DuplicateResourceException(message);
        }
        Comment saved = commentRepo.save(comment);
        return builderService.toCommentDto(saved);


    }

    public void findById(Long id) {

    }

}
