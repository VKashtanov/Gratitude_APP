package ru.kashtanov.like_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.like_service.dto.LikeDto;
import ru.kashtanov.like_service.dto.LikeSaveDto;
import ru.kashtanov.like_service.model.Like;
import ru.kashtanov.like_service.service.LikeService;

import java.net.URI;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<LikeDto> createLike(@RequestBody LikeSaveDto likeSaveDto) {
        System.out.println("Creating like");
        LikeDto likeDto = likeService.addLike(likeSaveDto);
        URI uri = URI.create("/api/v1/likes/" + likeDto.getId());
        return ResponseEntity.created(uri).body(likeDto);
    }

// only user that left comment is able to delete it
    @DeleteMapping
    public ResponseEntity<LikeDto> deleteLike(LikeSaveDto likeSaveDto) {
        LikeDto likeDto = likeService.deleteLike(likeSaveDto);
        return ResponseEntity.ok(likeDto);
    }
}
