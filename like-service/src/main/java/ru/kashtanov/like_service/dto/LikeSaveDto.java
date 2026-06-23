package ru.kashtanov.like_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.like_service.enums.EntityType;

import java.time.LocalDateTime;

/**
 * @author Viktor Кashtanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeSaveDto {
    private Long userId;
    private Long entityTypeId;
    private Long targetId;
}
