package ru.kashtanov.comment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.comment_service.enums.TargetType;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private TargetType targetType;
    private String comment;
    private String header;
    private String description;
    private Long targetId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) && targetType == that.targetType && Objects.equals(comment, that.comment) && Objects.equals(targetId, that.targetId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targetType, comment, targetId, userId);
    }
}
