package ru.kashtanov.gratitude_service.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;
import ru.kashtanov.gratitude_service.dto.GratitudeDto;
import ru.kashtanov.gratitude_service.service.GratitudeService;

import java.net.URI;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/v1/gratitude")
public class GratitudeController {


    private final GratitudeService gratitudeService;

    public GratitudeController(GratitudeService gratitudeService) {
        this.gratitudeService = gratitudeService;
    }

    // =============== CRUD API ==========================================
    @PostMapping
    public ResponseEntity<GratitudeDto> createGratitude(@RequestBody GratitudeCreateDto dto) {
        GratitudeDto gratitude = gratitudeService.createGratitude(dto);
        URI uri = URI.create("/api/v1/gratitude/" + gratitude.getId());
        return ResponseEntity.created(uri).body(gratitude);
    }

    @PostMapping("/{targetId}/like")
    public ResponseEntity<?> addLike(@PathVariable("targetId") String targetId) {
        return null;
    }

}
