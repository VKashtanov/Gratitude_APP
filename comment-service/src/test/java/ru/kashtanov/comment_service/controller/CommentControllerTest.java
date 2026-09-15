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
import ru.kashtanov.comment_service.enums.TargetType;
import ru.kashtanov.comment_service.service.CommentService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;

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
    private CommentDto commentDto;
    private final Long commentId = 1L;
    private final Long userId = 1L;
    private final Long targetId = 1L;
    private final TargetType targetType = TargetType.COMMENT;
    private final String text = "Creating comment";


    @BeforeEach
    void setUp() {
        createReq = new CommentCreateRequest(userId, targetId, targetType, text);
        commentDto = CommentDto.builder()
                .id(commentId)
                .userId(userId)
                .targetId(targetId)
                .targetType(targetType)
                .comment(text).build();
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.targetId").value(targetId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(text));
            verify(commentService, times(1)).createComment(any(CommentCreateRequest.class));
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
            notValidCreateReq = new CommentCreateRequest(userId, null, targetType, text);
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
            notValidCreateReq = new CommentCreateRequest(null, targetId, targetType, text);
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
            notValidCreateReq = new CommentCreateRequest(userId, targetId, null, text);
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
            notValidCreateReq = new CommentCreateRequest(userId, targetId, targetType, null);
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

            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/" + commentId))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(text))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.targetId").value(targetId));
            verify(commentService, times(1)).fetchCommentById(commentId);
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


}