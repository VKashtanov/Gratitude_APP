package ru.kashtanov.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.news_service.enums.ContentEnumType;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaMetaDataDto {
    private String info;
    private Long creatorId;
}
