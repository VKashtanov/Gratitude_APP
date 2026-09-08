package ru.kashtanov.comment_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.request.CommentDto;
import ru.kashtanov.comment_service.service.CommentService;

import java.net.URI;
import java.net.URL;

/**
 * @author Viktor Кashtanov
 */
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
        URI uri = URI.create("/api/v1/comments/");
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public CommentDto fetchById(@PathVariable Long id) {

        return new CommentDto();
    }

}
