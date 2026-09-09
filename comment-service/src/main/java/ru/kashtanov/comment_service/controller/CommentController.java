package ru.kashtanov.comment_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.request.CommentDto;
import ru.kashtanov.comment_service.dto.response.PaginatedCommentsDto;
import ru.kashtanov.comment_service.service.CommentService;

import java.net.URI;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@Validated
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@Valid @RequestBody CommentCreateRequest request) {
        CommentDto response = commentService.createComment(request);
        URI uri = URI.create("/api/v1/comments/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> fetchById(@Min(1) @PathVariable Long id) {
        CommentDto response = commentService.fetchCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public PaginatedCommentsDto fetchByUserId(@Min(1) @PathVariable Long userId,
                                              @RequestParam(value = "cursor", required = false) Long cursor,
                                              @Min(1) @Max(100) @RequestParam(value = "limit", defaultValue = "10") Long limit) {
        return commentService.fetchCommentByUserId(userId, cursor, limit);
    }

    @GetMapping("/target/{targetId}")
    public PaginatedCommentsDto fetchByTargetId(@Min(1) @PathVariable Long targetId,
                                                @RequestParam(value = "cursor", required = false) Long cursor,
                                                @Min(1) @Max(100) @RequestParam(value = "limit", defaultValue = "10") Long limit) {
        return commentService.fetchCommentByTargetId(targetId, cursor, limit);
    }

}
