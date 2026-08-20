package ru.kashtanov.subscription_service.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.service.SubscriptionService;

import java.net.URI;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto dto) {
        URI uri = URI.create("/api/subscription");
        SubscriptionDto response = subscriptionService.create(dto);
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> findSubscriptionById(@PathVariable(name = "id") Long id) {
        SubscriptionDto response = subscriptionService.fetchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public List<SubscriptionDto> findSubscriptionsByUserId(
            @PathVariable(name = "id") Long userId,
            @RequestParam(name = "cursor") int cursor,
            @RequestParam(name = "limit") int limit) {
        return subscriptionService.fetchByUserId(userId, cursor, limit);
    }

    @GetMapping("/target/{id}")
    public List<SubscriptionDto> findSubscriptionsByTargetId(@PathVariable(name = "id") Long targetId) {
        return subscriptionService.fetchByTargetId(targetId);

    }

    @GetMapping("/all")
    public List<SubscriptionDto> findAllSubscriptions(@PageableDefault Pageable pageable) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubscriptionDto> deleteSubscriptionsById(@PathVariable(name = "id") Long id) {
        return null;
    }

    @PutMapping
    public ResponseEntity<SubscriptionDto> changeSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        return null;
    }
}
