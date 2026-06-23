package ru.kashtanov.gratitude_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GratitudeCreateDto {
    private Long authorId;
    private String content;
    private List<Long> recipientIds;
}
