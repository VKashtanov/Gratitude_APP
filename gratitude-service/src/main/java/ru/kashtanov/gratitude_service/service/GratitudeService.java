package ru.kashtanov.gratitude_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;
import ru.kashtanov.gratitude_service.dto.GratitudeDto;
import ru.kashtanov.gratitude_service.exception.NotValidGratitudeCreateDtoException;
import ru.kashtanov.gratitude_service.model.Gratitude;
import ru.kashtanov.gratitude_service.repository.GratitudeRepo;
import ru.kashtanov.gratitude_service.util.GratitudeMapperService;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

/**
 * @author Viktor Кashtanov
 */
@Service
public class GratitudeService {

    private final RedisTemplate<String, String> redisTemplate;
    private final GratitudeRepo gratitudeRepo;
    private final GratitudeMapperService mapperService;
    private final ValidationService validationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GratitudeService(RedisTemplate<String, String> redisTemplate, GratitudeRepo gratitudeRepo, GratitudeMapperService mapperService, ValidationService validationService) {
        this.redisTemplate = redisTemplate;
        this.gratitudeRepo = gratitudeRepo;
        this.mapperService = mapperService;
        this.validationService = validationService;
    }

    @Transactional
    public GratitudeDto createGratitude(GratitudeCreateDto dto) {
        if (!validationService.isValid(dto)) {
            throw new NotValidGratitudeCreateDtoException("DTO fields are not valid", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Gratitude entity = mapperService.toEntity(dto);
        Gratitude save = gratitudeRepo.save(entity);
        GratitudeDto savedDto = mapperService.toDto(save);
        // Кешируем ТОЛЬКО после коммита
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        cacheGratitude(savedDto);
                    }
                }
        );
        return savedDto;
    }

    public void cacheGratitude(GratitudeDto dto) {
        String parsed = objectMapper.writeValueAsString(dto);
        String key = String.valueOf(dto.getId());
        redisTemplate.opsForValue().set(key, parsed, Duration.ofMinutes(5));
    }
}
