package ru.kashtanov.comment_service.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.dto.request.CommentUpdateRequest;
import ru.kashtanov.comment_service.dto.response.PaginatedCommentsDto;

import ru.kashtanov.comment_service.exceptions.ResourceNotFoundException;
import ru.kashtanov.comment_service.model.Comment;
import ru.kashtanov.comment_service.repo.CommentRepo;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
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
        Comment saved = commentRepo.save(comment);
        return builderService.toCommentDto(saved);


    }


    @Transactional
    public CommentDto fetchCommentById(Long id) {
        Comment comment = findCommentById(id);
        return builderService.toCommentDto(comment);
    }


    @Transactional(readOnly = true)
    public PaginatedCommentsDto fetchCommentByUserId(Long userId, Long cursor, Long limit) {
        return formPaginatedComments(
                cursor,
                limit,
                (curs, lim) -> commentRepo.findByUserId(userId, curs, lim));
    }


    @Transactional(readOnly = true)
    public PaginatedCommentsDto fetchCommentByTargetId(Long targetId, Long cursor, Long limit) {
        return formPaginatedComments(
                cursor,
                limit,
                (curs, lim) -> commentRepo.findByTargetId(targetId, curs, lim));
    }


    @Transactional(readOnly = true)
    public PaginatedCommentsDto fetchAll(Long cursor, Long limit) {
        return formPaginatedComments(
                cursor,
                limit,
                (curs, lim) -> commentRepo.findAllComments(curs, lim));
    }


    @Transactional
    public void deleteCommentById(Long id) {
        Comment comment = findCommentById(id);
        commentRepo.delete(comment);
        log.info("Comment with id: {} has been deleted", id);
    }

    @Transactional
    public CommentDto updateCommentById(Long id, CommentUpdateRequest dto) {
        Comment comment = findCommentById(id);

        if (dto.getComment() != null) comment.setComment(dto.getComment());
        if (dto.getDescription() != null) comment.setDescription(dto.getDescription());
        if (dto.getHeader() != null) comment.setHeader(dto.getHeader());
        commentRepo.save(comment);
        return builderService.toCommentDto(comment);
    }


    private Comment findCommentById(Long id) {
        return commentRepo.findById(id).orElseThrow(
                () -> {
                    String msg = String.format("Comment with id: %d not found", id);
                    return new ResourceNotFoundException(msg);
                }
        );
    }

    private PaginatedCommentsDto formPaginatedComments(Long cursor,
                                                       Long limit,
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
