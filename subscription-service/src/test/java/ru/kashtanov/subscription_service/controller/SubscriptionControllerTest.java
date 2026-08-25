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
import ru.kashtanov.subscription_service.dto.SubscriptionDto;
import ru.kashtanov.subscription_service.exceptions.SubscriptionCrudException;
import ru.kashtanov.subscription_service.service.SubscriptionService;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Viktor Кashtanov
 */
@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubscriptionService subscriptionService;

    // Variables initialization
    private SubscriptionDto reqeustdto = new SubscriptionDto();

    @BeforeEach
    void setUp() {
        reqeustdto = new SubscriptionDto();
        reqeustdto.setId(1L);
        reqeustdto.setUserId(1L);
        reqeustdto.setTargetId(1L);
    }

    @Test
    void createSubscription_Scenario_1_Positive() throws Exception {
        String json = objectMapper.writeValueAsString(reqeustdto);
        when(subscriptionService.create(any(SubscriptionDto.class))).thenReturn(reqeustdto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subscription")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(reqeustdto.getId()))
                .andExpect(jsonPath("$.userId").value(reqeustdto.getUserId()))
                .andExpect(jsonPath("$.targetId").value(reqeustdto.getTargetId()));
        verify(subscriptionService, times(1)).create(any(SubscriptionDto.class));
    }

    @Test
    void createSubscription_Scenario_2_InvalidDto() throws Exception {
        SubscriptionDto dto = new SubscriptionDto();
        String emptyJson = objectMapper.writeValueAsString(dto);
        when(subscriptionService.create(any(SubscriptionDto.class))).thenThrow(SubscriptionCrudException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subscription")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(emptyJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Subscription CRUD exception"));
        verify(subscriptionService, times(1)).create(any(SubscriptionDto.class));
    }
}