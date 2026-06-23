package ru.kashtanov.gratitude_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GratitudeDto {
    private Long id;
    private Long authorId;
    private String content;
    private List<Long> recipientIds;
    private LocalDateTime timestamp;

}
