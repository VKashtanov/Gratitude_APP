package ru.kashtanov.subscription_service.controller;

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
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumStatus;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumType;
import ru.kashtanov.subscription_service.service.SubscriptionService;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Viktor Кashtanov
 */
@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerFindByIdTest {

    @MockitoBean
    private SubscriptionService subscriptionService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    //Test variables
    private SubscriptionDto response;
    private final Long subscriptionId = 1L;

    @BeforeEach
    void setUp() {
        Long userId = 2L;
        Long targetId = 3L;
        Instant createdAtTime = Instant.parse("2026-12-01T10:00:00Z");
        Instant finishedAtTime = Instant.parse("2026-12-02T12:00:00Z");

        response = new SubscriptionDto();
        response.setId(subscriptionId);
        response.setUserId(userId);
        response.setTargetId(targetId);
        response.setType(SubscriptionEnumType.DEFAULT);
        response.setStatus(SubscriptionEnumStatus.ACTIVE);
        response.setCreatedAt(createdAtTime);
        response.setUpdatedAt(finishedAtTime);
        response.setExpiresAt(finishedAtTime);

    }

    @Test
    void findById_PositiveScenario1() throws Exception {
        when(subscriptionService.fetchById(subscriptionId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subscription/{id}", subscriptionId) )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(subscriptionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetId").value(response.getTargetId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()));
        verify(subscriptionService, times(1)).fetchById(subscriptionId);
    }


}