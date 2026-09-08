package ru.kashtanov.comment_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.kashtanov.comment_service.enums.TargetType;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @NotNull(message = "Target type must not be Null")
    private TargetType targetType;

    @NotNull(message = "Comment must not be Null")
    @NotBlank(message = "Comment must not be blank")
    private String comment;

    @NotNull(message = "Target ID must not be Null")
    private Long targetId;

    @NotNull(message = "User ID must not be Null")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentCreateRequest that = (CommentCreateRequest) o;
        return targetType == that.targetType && Objects.equals(comment, that.comment) && Objects.equals(targetId, that.targetId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType, comment, targetId, userId);
    }
}
