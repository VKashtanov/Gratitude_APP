package ru.kashtanov.news_service.service;

import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.model.Content;
import ru.kashtanov.news_service.repo.ContentRepo;

import java.util.Collections;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Service
public class ContentService {

    private final ContentRepo contentRepo;

    public ContentService(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }

    public List<Content> fetchContentByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return contentRepo.findByIds(ids);
    }
}
