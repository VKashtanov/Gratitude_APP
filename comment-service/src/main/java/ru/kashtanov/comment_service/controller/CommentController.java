package ru.kashtanov.comment_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kashtanov.comment_service.dto.PaginatedParam;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.dto.request.CommentUpdateRequest;
import ru.kashtanov.comment_service.dto.response.PaginatedCommentsDto;
import ru.kashtanov.comment_service.service.CommentService;

import java.net.URI;

/**
 * @author Viktor Кashtanov
 */
@Validated
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@Valid @RequestBody CommentCreateRequest request) {
        CommentDto response = commentService.createComment(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> fetchById(@Positive @PathVariable Long id) {
        CommentDto response = commentService.fetchCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PaginatedCommentsDto> fetchByUserId(@Positive @PathVariable Long userId,
                                                              @Valid PaginatedParam param) {
        PaginatedCommentsDto response = commentService.fetchCommentByUserId(userId, param.cursor(), param.limit());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/target/{targetId}")
    public ResponseEntity<PaginatedCommentsDto> fetchByTargetId(@Positive @PathVariable Long targetId,
                                                                @Valid PaginatedParam param) {
        PaginatedCommentsDto response = commentService.fetchCommentByTargetId(targetId, param.cursor(), param.limit());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedCommentsDto> fetchAll(@Valid PaginatedParam param) {
        PaginatedCommentsDto response = commentService.fetchAll(param.cursor(), param.limit());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Positive @PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateById(@Positive @PathVariable Long id,
                                                 @Valid @RequestBody CommentUpdateRequest dto) {
        CommentDto response = commentService.updateCommentById(id, dto);
        return ResponseEntity.ok(response);
    }


}
