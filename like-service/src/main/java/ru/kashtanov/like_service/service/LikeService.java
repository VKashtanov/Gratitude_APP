package ru.kashtanov.like_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kashtanov.like_service.dto.LikeDto;
import ru.kashtanov.like_service.dto.LikeSaveDto;
import ru.kashtanov.like_service.exception.LikeNotFoundException;
import ru.kashtanov.like_service.exception.LikeNotSavedException;
import ru.kashtanov.like_service.model.Like;
import ru.kashtanov.like_service.model.LikeEntityType;
import ru.kashtanov.like_service.repository.LikeRepo;
import ru.kashtanov.like_service.util.LikeMapperService;
import ru.kashtanov.like_service.util.ValidationService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author Viktor Кashtanov
 */
@Service
public class LikeService {
    private final LikeRepo likeRepo;
    private final LikeMapperService mapperService;
    private final ValidationService validationService;
    private final LikeEntityService likeEntityService;

    public LikeService(LikeRepo likeRepo, LikeMapperService mapperService, ValidationService validationService, LikeEntityService likeEntityService) {
        this.likeRepo = likeRepo;
        this.mapperService = mapperService;
        this.validationService = validationService;
        this.likeEntityService = likeEntityService;
    }

    // =================== CRUD ============================

    public LikeDto findLikeById(Long likeId) {
        Like like = likeRepo.findById(likeId)
                .orElseThrow(() -> new LikeNotFoundException("Not_found! ", HttpStatus.NOT_FOUND));
        return mapperService.toDto(like);
    }

    public LikeDto addLike(LikeSaveDto dto) {
        if (!validationService.isValid(dto)) {
            throw new LikeNotSavedException("Fields are not valid ", HttpStatus.UNPROCESSABLE_CONTENT);
        }
        LikeEntityType byId = likeEntityService.findById(dto.getEntityTypeId());
        Like entity = mapperService.toEntity(dto);
        entity.setLikeEntityType(byId);
        entity.setTimestamp(Instant.now());
        Like save = likeRepo.save(entity);
        return mapperService.toDto(save);
    }

    @Transactional
    public LikeDto deleteLike(LikeDto dto) {
        LikeDto likeById = findLikeById(dto.getId());
        likeRepo.deleteById(likeById.getId());
        return likeById;
    }
}
