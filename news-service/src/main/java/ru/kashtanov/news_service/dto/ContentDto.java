package ru.kashtanov.news_service.dto;

import lombok.*;
import ru.kashtanov.news_service.enums.ContentEnumType;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String info;
    private ContentEnumType contentType;
    private Long creatorId;
    private Instant creationDate;
    private Long size;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContentDto that = (ContentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(originalFileName, that.originalFileName) && Objects.equals(storedFileName, that.storedFileName) && Objects.equals(info, that.info) && contentType == that.contentType && Objects.equals(creatorId, that.creatorId) && Objects.equals(creationDate, that.creationDate) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalFileName, storedFileName, info, contentType, creatorId, creationDate, size);
    }
}
