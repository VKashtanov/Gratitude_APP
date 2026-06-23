package ru.kashtanov.gratitude_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.kashtanov.gratitude_service.dto.GratitudeCreateDto;
import ru.kashtanov.gratitude_service.dto.GratitudeDto;
import ru.kashtanov.gratitude_service.exception.NotValidGratitudeCreateDtoException;
import ru.kashtanov.gratitude_service.model.Gratitude;
import ru.kashtanov.gratitude_service.repository.GratitudeRepo;
import ru.kashtanov.gratitude_service.util.GratitudeMapperService;

/**
 * @author Viktor Кashtanov
 */
@Service
public class GratitudeService {
    private final GratitudeRepo gratitudeRepo;
    private final GratitudeMapperService mapperService;
    private final ValidationService validationService;

    public GratitudeService(GratitudeRepo gratitudeRepo, GratitudeMapperService mapperService, ValidationService validationService) {
        this.gratitudeRepo = gratitudeRepo;
        this.mapperService = mapperService;
        this.validationService = validationService;
    }

    public GratitudeDto createGratitude(GratitudeCreateDto dto) {
        if (!validationService.isValid(dto)) {
            throw new NotValidGratitudeCreateDtoException("DTO fields are not valid", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Gratitude entity = mapperService.toEntity(dto);
        Gratitude save = gratitudeRepo.save(entity);
        return mapperService.toDto(save);

    }
}
