package ru.kashtanov.gratitude_service.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;
import ru.kashtanov.gratitude_service.dto.GratitudeDto;
import ru.kashtanov.gratitude_service.service.GratitudeService;

import java.net.URI;

/**
 * @author Viktor Кashtanov
 */
@RestController("api/v1/gratitude")
public class GratitudeController {

    private final GratitudeService gratitudeService;

    public GratitudeController(GratitudeService gratitudeService) {
        this.gratitudeService = gratitudeService;
    }

    // =============== CRUD API ==========================================
    @PostMapping
    public ResponseEntity<GratitudeDto> createGratitude(GratitudeCreateDto dto) {
        GratitudeDto gratitude = gratitudeService.createGratitude(dto);
        URI uri = URI.create("/api/v1/gratitude/" + gratitude.getId());
        return ResponseEntity.created(uri).body(gratitude);
    }

    @PostMapping("/{targetId}/like")
    public ResponseEntity<?> addLike(@PathVariable("targetId") String targetId) {
        return null;
    }

}
