package ru.kashtanov.comment_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.kashtanov.comment_service.enums.TargetType;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class CommentUpdateRequest {
    @NotNull(message = "Comment must not be Null")
    @NotBlank(message = "Comment must not be Blank")
    @Size(min = 1, max = 1000)
    private String comment;

    @Size(max = 300)
    private String header;

    @Size(max = 500)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentUpdateRequest that = (CommentUpdateRequest) o;
        return Objects.equals(comment, that.comment) && Objects.equals(header, that.header) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, header, description);
    }
}
