package ru.kashtanov.comment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashtanov.comment_service.dto.request.CommentDto;

import java.util.List;
import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedCommentsDto {
    private List<CommentDto> comments;
    private boolean hasMore;
    private Long nextCursor;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaginatedCommentsDto that = (PaginatedCommentsDto) o;
        return Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(comments);
    }
}
