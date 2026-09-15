package ru.kashtanov.comment_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.model.Comment;
import ru.kashtanov.comment_service.repo.CommentRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentBuilderService builderService;
    @Mock
    private CommentRepo commentRepo;

    // Variables
    private CommentCreateRequest createReq;
    private Comment newComment;
    private Comment savedComment;
    private CommentDto savedCommentDto;
    private final Long commentId = 1L;
    private final Long userId = 1L;
    private final Long targetId = 1L;
    private final TargetType targetType = TargetType.COMMENT;
    private final String text = "Creating comment";

    @BeforeEach
    void setUp() {
        createReq = new CommentCreateRequest(userId, targetId, targetType, text);
        newComment = Comment.builder()
                .userId(userId)
                .targetId(targetId)
                .comment(text)
                .targetType(targetType).build();

        savedComment = Comment.builder()
                .id(commentId)
                .userId(userId)
                .targetId(targetId)
                .comment(text)
                .targetType(targetType).build();

        savedCommentDto = CommentDto.builder()
                .id(commentId)
                .userId(userId)
                .targetId(targetId)
                .comment(text)
                .targetType(targetType).build();

    }

    @Test
    void createComment_WhenCreateRequestIsValid() {
        //WHEN
        when(builderService.toComment(createReq)).thenReturn(newComment);
        when(commentRepo.save(newComment)).thenReturn(savedComment);
        when(builderService.toCommentDto(savedComment)).thenReturn(savedCommentDto);

        CommentDto comment = commentService.createComment(createReq);
        // THEN
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(commentId);
        assertThat(comment.getUserId()).isEqualTo(userId);
        assertThat(comment.getTargetId()).isEqualTo(targetId);
        assertThat(comment.getTargetType()).isEqualTo(targetType);
        assertThat(comment.getComment()).isEqualTo(text);
        verify(builderService, times(1)).toComment(any(CommentCreateRequest.class));
        verify(commentRepo, times(1)).save(any(Comment.class));
        verify(builderService, times(1)).toCommentDto(any(Comment.class));
    }

    @Test
    void createComment_WhenRepoThrows_Propagates() {
        // GIVEN
        when(builderService.toComment(createReq)).thenReturn(newComment);
        when(commentRepo.save(newComment)).thenThrow(new RuntimeException("DB down"));
        // WHEN / THEN
        assertThatThrownBy(() -> commentService.createComment(createReq))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB down");
    }


}