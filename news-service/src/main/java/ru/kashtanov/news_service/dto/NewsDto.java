package ru.kashtanov.news_service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Viktor Кashtanov
 */
@Data
public class NewsDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;


}
