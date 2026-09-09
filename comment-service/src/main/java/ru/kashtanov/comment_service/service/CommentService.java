package ru.kashtanov.comment_service.service;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.request.CommentDto;
import ru.kashtanov.comment_service.dto.response.PaginatedCommentsDto;
import ru.kashtanov.comment_service.exceptions.CommentCrudException;

import ru.kashtanov.comment_service.exceptions.DuplicateResourceException;
import ru.kashtanov.comment_service.exceptions.ResourceNotFoundException;
import ru.kashtanov.comment_service.model.Comment;
import ru.kashtanov.comment_service.repo.CommentRepo;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

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

    @Transactional
    public CommentDto fetchCommentById(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> {
                    String msg = String.format("Comment with id: %d not found", id);
                    return new ResourceNotFoundException(msg);
                }
        );
        return builderService.toCommentDto(comment);
    }

    //readOnly is for optimization shows to hibernate , there is no changing ops in method
    //readOnly prevent dirty checking mechanism,doesn't do flush, saves memory

    @Transactional(readOnly = true)
    public PaginatedCommentsDto fetchCommentByUserId(Long userId, Long cursor, Long limit) {
        return getPageComments(cursor,
                limit,
                (curs, lim) -> commentRepo.findByUserId(userId, curs, lim));
    }

    @Transactional(readOnly = true)
    public PaginatedCommentsDto fetchCommentByTargetId(Long targetId, Long cursor, Long limit) {
        return getPageComments(cursor,
                limit,
                (curs, lim) -> commentRepo.findByTargetId(targetId, curs, lim));
    }


    private PaginatedCommentsDto getPageComments(Long cursor, Long limit,
                                                 BiFunction<Long, Long, List<Comment>> fetcher) {
        List<Comment> comments = fetcher.apply(cursor, limit + 1); // limit+1 - to check if it hasMore

        List<CommentDto> page = comments.stream()
                .map(builderService::toCommentDto)
                .limit(limit)
                .toList();

        boolean hasMore = comments.size() > limit;
        Long nextCursor = null;

        if (hasMore) {
            nextCursor = comments.get(page.size() - 1).getId();
        }
        return new PaginatedCommentsDto(page, hasMore, nextCursor);


    }

}
