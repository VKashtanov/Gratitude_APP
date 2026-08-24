package ru.kashtanov.subscription_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.subscription_service.constants.SubscriptionConstant;
import ru.kashtanov.subscription_service.dto.PaginatedResponse;
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;
import ru.kashtanov.subscription_service.model.Subscription;
import ru.kashtanov.subscription_service.repo.SubscriptionRepo;
import ru.kashtanov.subscription_service.util.SubscriptionBuilderService;
import ru.kashtanov.subscription_service.util.ValidationService;

import java.util.Collections;
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
    public PaginatedResponse fetchByUserId(Long userId, Long cursor, Long limit) {
        if (userId == null) throw new SubscriptionCrudException("User ID is null");
        Long safeLimit = limit == null ? SubscriptionConstant.DEFAULT_LIMIT : limit;

        List<Subscription> list = repo.findByUserId(userId, cursor, safeLimit+1);
        return getPaginatedResponse(safeLimit, list);
    }


    @Transactional
    public PaginatedResponse fetchByTargetId(Long targetId, Long cursor, Long limit) {
        if (targetId == null) throw new SubscriptionCrudException("Target ID is null");
        Long safeLimit = limit == null ? SubscriptionConstant.DEFAULT_LIMIT : limit;

        List<Subscription> list = repo.findByTargetId(targetId, cursor, safeLimit + 1);
        return getPaginatedResponse(safeLimit, list);
    }

    private PaginatedResponse getPaginatedResponse(Long limit, List<Subscription> list) {
        Long nextCursor = null;
        boolean hasMore = false;
        if (list.isEmpty()) {
            return new PaginatedResponse();
        } else if (limit < list.size()) {
            hasMore = true;
            nextCursor = list.get(limit.intValue()).getId();
        }
        List<SubscriptionDto> subs = list.stream().map(builderService::buildDto)
                .limit(limit)
                .toList();

        return builderService.buildPaginatedResponse(subs, nextCursor, hasMore);
    }


}
