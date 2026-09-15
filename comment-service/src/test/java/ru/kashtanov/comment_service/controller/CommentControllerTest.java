package ru.kashtanov.comment_service.controller;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.kashtanov.comment_service.dto.request.CommentCreateRequest;
import ru.kashtanov.comment_service.dto.response.CommentDto;
import ru.kashtanov.comment_service.dto.response.PaginatedCommentsDto;
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.service.CommentService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Viktor Кashtanov
 */
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CommentService commentService;

    //Variables
    private CommentCreateRequest createReq;
    private CommentCreateRequest notValidCreateReq;
    private PaginatedCommentsDto paginatedResponseOk;
    private CommentDto commentDto;
    private CommentDto commentDto1;
    private CommentDto commentDto2;

    private final Long commentId1 = 1L;
    private final Long userId1 = 1L;
    private final Long targetId1 = 1L;

    private final Long commentId2 = 2L;
    private final Long userId2 = 2L;
    private final Long targetId2 = 2L;

    private final TargetType targetType = TargetType.COMMENT;
    private final String text = "Creating comment";


    @BeforeEach
    void setUp() {
        createReq = new CommentCreateRequest(userId1, targetId1, targetType, text);
        commentDto = CommentDto.builder()
                .id(commentId1)
                .userId(userId1)
                .targetId(targetId1)
                .targetType(targetType)
                .comment(text).build();

        commentDto1 = CommentDto.builder()
                .id(commentId1)
                .userId(userId1)
                .targetId(targetId1)
                .targetType(targetType)
                .comment(text).build();

        commentDto2 = CommentDto.builder()
                .id(commentId2)
                .userId(userId2)
                .targetId(targetId2)
                .targetType(targetType)
                .comment(text).build();

        paginatedResponseOk = new PaginatedCommentsDto();
        paginatedResponseOk.setComments(List.of(commentDto1, commentDto2));
        paginatedResponseOk.setHasMore(false);
    }


    @Nested
    class CreateComment {
        @Test
        void create_WhenOk() throws Exception {
            String json = objectMapper.writeValueAsString(createReq);
            when(commentService.createComment(createReq)).thenReturn(commentDto);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.targetId").value(targetId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(text));
            verify(commentService, times(1)).createComment(any(CommentCreateRequest.class));
        }

        @Test
        void create_WhenJsonInvalid_ReturnsBadRequest() throws Exception {
            String invalidJson   = "{ \"userId\": 1, }";
            when(commentService.createComment(createReq)).thenReturn(commentDto);
            Matcher<String> error = containsString("JSON parse error");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidJson))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
             ;
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }


        @Test
        void create_WhenRequestBodyIsNull() throws Exception {
            Matcher<String> error = containsString("Required request body is missing");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }

        @Test
        void create_WhenTargetIdNull() throws Exception {
            notValidCreateReq = new CommentCreateRequest(userId1, null, targetType, text);
            String json = objectMapper.writeValueAsString(notValidCreateReq);
            Matcher<String> message = containsString("Fields validation failed");
            Matcher<String> error = containsString("Target ID must not be Null");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }

        @Test
        void create_WhenUserIdNull() throws Exception {
            notValidCreateReq = new CommentCreateRequest(null, targetId1, targetType, text);
            String json = objectMapper.writeValueAsString(notValidCreateReq);
            Matcher<String> message = containsString("Fields validation failed");
            Matcher<String> error = containsString("User ID must not be Null");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }

        @Test
        void create_WhenTargetTypeNull() throws Exception {
            notValidCreateReq = new CommentCreateRequest(userId1, targetId1, null, text);
            String json = objectMapper.writeValueAsString(notValidCreateReq);
            Matcher<String> message = containsString("Fields validation failed");
            Matcher<String> error = containsString("Target type must not be Null");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }

        @Test
        void create_WhenCommentIsNull() throws Exception {
            notValidCreateReq = new CommentCreateRequest(userId1, targetId1, targetType, null);
            String json = objectMapper.writeValueAsString(notValidCreateReq);
            Matcher<String> message = containsString("Fields validation failed");
            Matcher<String> error = containsString("Comment must not be Null");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
            verify(commentService, never()).createComment(any(CommentCreateRequest.class));
        }
    }


    @Nested
    class FetchComment {
        @Test
        void fetchById_WhenOk() throws Exception {
            when(commentService.fetchCommentById(anyLong())).thenReturn(commentDto);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/" + commentId1))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(text))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.targetId").value(targetId1));
            verify(commentService, times(1)).fetchCommentById(commentId1);
        }

        @Test
        void fetchById_WhenIdIsZero_ReturnsBadRequest() throws Exception {
            Long zeroId = 0L;
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/" + zeroId))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(containsString("Fields are equal to null")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("fetchById.id: must be greater than 0"));
            verify(commentService, never()).fetchCommentById(zeroId);
        }

    }

    @Nested
    class FetchCommentByUserId {

        @Test
        void fetchCommentByUserId_WhenOk() throws Exception {
            Long cursor = 0L;
            Long limit = 5L;
            when(commentService.fetchCommentByUserId(userId1, cursor, limit)).thenReturn(paginatedResponseOk);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/user/" + userId1)
                            .param("cursor", cursor.toString())
                            .param("limit", limit.toString()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.hasMore").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].targetId").value(targetId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].userId").value(userId1))

                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].id").value(commentId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].targetId").value(targetId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].userId").value(userId2));
            verify(commentService, times(1)).fetchCommentByUserId(userId1, cursor, limit);
        }


        @Test
        void fetchCommentByUserId_WhenUserIdIsNegative() throws Exception {
            long cursor = 0L;
            long limit = 5L;
            long negativeId = -1L;
            Matcher<String> error = containsString("fetchByUserId.userId: must be greater than 0");
            Matcher<String> message = containsString("Fields are equal to null or empty");

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/user/" + negativeId)
                            .param("cursor", Long.toString(cursor))
                            .param("limit", Long.toString(limit)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message));
            verify(commentService, never()).fetchCommentByUserId(negativeId, cursor, limit);
        }

        @Test
        void fetchCommentByUserId_WhenCursorIsNull() throws Exception {
            long limit = 5L;
            when(commentService.fetchCommentByUserId(userId1, null, limit)).thenReturn(paginatedResponseOk);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/user/" + userId1)
                            .param("limit", Long.toString(limit)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.hasMore").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].targetId").value(targetId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].userId").value(userId1))

                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].id").value(commentId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].targetId").value(targetId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].userId").value(userId2));
            verify(commentService, times(1)).fetchCommentByUserId(userId1, null, limit);
        }

        @Test
        void fetchCommentByUserId_WhenLimitIsNull() throws Exception {
            long cursor = 1L;
            when(commentService.fetchCommentByUserId(userId1, cursor, null)).thenReturn(paginatedResponseOk);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/user/" + userId1)
                            .param("cursor", Long.toString(cursor)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.hasMore").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].targetId").value(targetId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].userId").value(userId1))

                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].id").value(commentId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].targetId").value(targetId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].userId").value(userId2));
            verify(commentService, times(1)).fetchCommentByUserId(userId1, cursor, null);
        }


    }


    @Nested
    class FetchCommentByTargetId {

        @Test
        void fetchCommentByTargetId_WhenOk() throws Exception {
            Long cursor = 0L;
            Long limit = 5L;
            when(commentService.fetchCommentByTargetId(targetId1, cursor, limit)).thenReturn(paginatedResponseOk);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/target/" + targetId1)
                            .param("cursor", cursor.toString())
                            .param("limit", limit.toString()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.hasMore").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].id").value(commentId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].targetId").value(targetId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].userId").value(userId1))

                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].id").value(commentId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].comment").value(containsString(text)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].targetId").value(targetId2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].userId").value(userId2));
            verify(commentService, times(1)).fetchCommentByTargetId(targetId1, cursor, limit);
        }

    }


}