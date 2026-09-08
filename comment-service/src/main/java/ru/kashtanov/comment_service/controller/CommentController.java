package ru.kashtanov.comment_service.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.request.CommentDto;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @PostMapping
    public CommentDto create(@Valid @RequestBody CommentCreateRequest request) {
        System.out.println("GOT IT -- " + request.toString());
        return new CommentDto();
    }

    @GetMapping("/{id}")
    public CommentDto fetchById(@PathVariable Long id) {

        return new CommentDto();
    }

}
