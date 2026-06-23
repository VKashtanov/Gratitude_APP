package ru.kashtanov.like_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.like_service.enums.EntityType;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */

@NoArgsConstructor
@Data
public class LikeDto {
    private Long id;
    private Long userId;
    private EntityType entityType;
    private Long targetId;
    private Instant timestamp;
}
