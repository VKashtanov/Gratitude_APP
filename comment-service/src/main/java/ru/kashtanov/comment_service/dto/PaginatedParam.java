package ru.kashtanov.comment_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Viktor Кashtanov
 */

public record PaginatedParam(
        @PositiveOrZero
        @RequestParam(value = "cursor", required = false)
        Long cursor,

        @Min(MIN_LIMIT)
        @Max(MAX_LIMIT)
        @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT_VALUE)
        Long limit
)
{
    public static final int MIN_LIMIT = 1;
    public static final int MAX_LIMIT = 100;
    public static final String DEFAULT_LIMIT_VALUE = "10";
}
