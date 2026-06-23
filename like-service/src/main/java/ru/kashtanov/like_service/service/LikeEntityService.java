package ru.kashtanov.like_service.service;

import org.springframework.stereotype.Service;
import ru.kashtanov.like_service.model.LikeEntityType;
import ru.kashtanov.like_service.repository.LikeEntityRepo;

/**
 * @author Viktor Кashtanov
 */
@Service
public class LikeEntityService {

    private final LikeEntityRepo likeEntityRepo;

    public LikeEntityService(LikeEntityRepo likeEntityRepo) {
        this.likeEntityRepo = likeEntityRepo;
    }

    public LikeEntityType findById(Long id) {
        return likeEntityRepo.findById(id).get();
    }
}
