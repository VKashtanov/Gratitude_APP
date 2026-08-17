package ru.kashtanov.news_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kashtanov.news_service.dto.ContentDto;
import ru.kashtanov.news_service.dto.MediaMetaDataDto;
import ru.kashtanov.news_service.enums.ContentEnumType;
import ru.kashtanov.news_service.exceptions.ContentValidationException;
import ru.kashtanov.news_service.exceptions.MinioS3CustomException;
import ru.kashtanov.news_service.model.Content;
import ru.kashtanov.news_service.repo.ContentRepo;
import ru.kashtanov.news_service.util.ContentValidationService;

import java.io.File;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class ContentService {

    private final ContentRepo contentRepo;
    private final MinioService minioService;
    private final ContentValidationService validationService;

    public ContentService(ContentRepo contentRepo, MinioService minioService, ContentValidationService validationService) {
        this.contentRepo = contentRepo;
        this.minioService = minioService;
        this.validationService = validationService;
    }

    @Transactional
    public List<Content> fetchContentByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return contentRepo.findByIds(ids);
    }

    @Transactional
    public ContentDto createContent(MediaMetaDataDto dto, MultipartFile file) {
        if (dto == null || file == null) {
            throw new ContentValidationException("File can't be processed since it is empty");
        }

        String uniqueName = minioService.uploadMedia(file);
        long size = file.getSize();
        ContentEnumType enumType = determineContentType(file.getContentType());

        var contDto = new ContentDto();
        contDto.setOriginalFileName(file.getOriginalFilename());
        contDto.setInfo(dto.getInfo());
        contDto.setCreatorId(dto.getCreatorId());
        contDto.setSize(size);
        contDto.setStoredFileName(uniqueName);
        contDto.setContentType(enumType);

        return save(contDto);
    }

    @Transactional
    public ContentDto save(ContentDto contentDto) {
        validationService.validateCommon(contentDto);
        Content content = toContent(contentDto);
        contentRepo.save(content);
        return toContentDto(content);
    }

    private ContentEnumType determineContentType(String type) {
        if (type == null || type.isEmpty()) {
            throw new ContentValidationException("Type can't be empty");
        }
        if (type.contains("text")) {
            return ContentEnumType.TEXT;
        } else if (type.contains("image")) {
            return ContentEnumType.IMAGE;
        } else if (type.contains("video")) {
            return ContentEnumType.VIDEO;
        } else if (type.contains("audio")) {
            return ContentEnumType.AUDIO;
        } else {
            return ContentEnumType.OTHER;
        }
    }

    private Content toContent(ContentDto dto) {
        Instant creationDate = dto.getCreationDate();
        if (creationDate == null) {
            creationDate = Instant.now();
        }
        var content = new Content();
        content.setId(dto.getId());
        content.setOriginalFileName(dto.getOriginalFileName());
        content.setStoredFileName(dto.getStoredFileName());
        content.setInfo(dto.getInfo());
        content.setCreatorId(dto.getCreatorId());
        content.setCreationDate(creationDate);
        content.setSize(dto.getSize());
        content.setContentType(dto.getContentType());
        return content;

    }

    private ContentDto toContentDto(Content content) {
        var dto = new ContentDto();
        dto.setId(content.getId());
        dto.setOriginalFileName(content.getOriginalFileName());
        dto.setStoredFileName(content.getStoredFileName());
        dto.setInfo(content.getInfo());
        dto.setCreatorId(content.getCreatorId());
        dto.setCreationDate(content.getCreationDate());
        dto.setSize(content.getSize());
        dto.setContentType(content.getContentType());
        return dto;

    }
}
