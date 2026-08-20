package ru.kashtanov.subscription_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;
import ru.kashtanov.subscription_service.model.Subscription;
import ru.kashtanov.subscription_service.repo.SubscriptionRepo;
import ru.kashtanov.subscription_service.util.SubscriptionBuilderService;
import ru.kashtanov.subscription_service.util.ValidationService;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class SubscriptionService {
    private final SubscriptionRepo repo;
    private final ValidationService validationService;
    private final SubscriptionBuilderService builderService;

    public SubscriptionService(SubscriptionRepo repo, ValidationService validationService, SubscriptionBuilderService builderService) {
        this.repo = repo;
        this.validationService = validationService;
        this.builderService = builderService;
    }

    // CRUD
    @Transactional
    public SubscriptionDto create(SubscriptionDto dto) {
        validationService.validateCommon(dto);
        Subscription subscription = builderService.buildSubscription(dto);
        Subscription saved = repo.save(subscription);
        return builderService.buildDto(saved);
    }

    @Transactional
    public SubscriptionDto fetchById(Long id) {
        if (id == null) throw new SubscriptionCrudException("ID is null");
        Subscription subscription = repo.findById(id).orElseThrow(() -> new SubscriptionCrudException("Subscription with id " + id + " not found"));
        return builderService.buildDto(subscription);
    }

    @Transactional
    public List<SubscriptionDto> fetchByUserId(Long userId,int cursor, int limit) {
        if (userId == null) throw new SubscriptionCrudException("User ID is null");

        List<Subscription> list = repo.findByUserId(userId, (long) cursor, limit);
        return list.stream()
                .map(builderService::buildDto).toList();
    }

    @Transactional
    public List<SubscriptionDto> fetchByTargetId(Long targetId) {
        if (targetId == null) throw new SubscriptionCrudException("Target ID is null");
        List<Subscription> list = repo.findByTargetId(targetId);
        return list.stream()
                .map(builderService::buildDto).toList();
    }
}
