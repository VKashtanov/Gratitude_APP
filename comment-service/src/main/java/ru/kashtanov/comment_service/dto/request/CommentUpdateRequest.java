package ru.kashtanov.comment_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kashtanov.comment_service.enums.TargetType;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class CommentUpdateRequest {
    @NotNull(message = "Target type should not be Null")
    private TargetType targetType;

    @NotNull(message = "Comment must not be Null")
    @NotBlank(message = "Comment must not be Blank")
    private String comment;

    @NotNull(message = "Target ID should not be Null")
    private Long targetId;

    @NotNull(message = "User ID should not be Null")
    private Long user_id;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentUpdateRequest that = (CommentUpdateRequest) o;
        return targetType == that.targetType && Objects.equals(comment, that.comment) && Objects.equals(targetId, that.targetId) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType, comment, targetId, user_id);
    }
}
