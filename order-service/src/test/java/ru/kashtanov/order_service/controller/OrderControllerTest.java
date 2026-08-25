package ru.kashtanov.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.kashtanov.order_service.dto.OrderDto;
import ru.kashtanov.order_service.dto.OrderPlacedEvent;
import ru.kashtanov.order_service.dto.OrderSaveDto;
import ru.kashtanov.order_service.enums.OrderStatus;
import ru.kashtanov.order_service.service.OrderProducerService;
import ru.kashtanov.order_service.service.OrderService;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Viktor Кashtanov
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderProducerService producerService;

    @MockBean
    private OrderService orderService;


    //Variables
    private OrderPlacedEvent event;
    private OrderSaveDto orderRequestDto;
    private OrderDto orderDto;

    @BeforeEach
    public void setUp() {
        Instant createdTime = Instant.parse("2026-12-12T10:10:00Z");
        Instant finishedTime = Instant.parse("2026-12-12T10:10:00Z");
        List<Long> productIds = List.of(10L, 20L);
        Long orderId = 1L;
        Long userId = 1L;

        event = new OrderPlacedEvent();
        event.setOrderId(orderId);
        event.setOrderPrice(100.0);
        event.setUserEmail("test@email.com");
        event.setOrderDate(createdTime);

        orderRequestDto = new OrderSaveDto();
        orderRequestDto.setCreatedAt(createdTime);
        orderRequestDto.setStatus(OrderStatus.APPROVED);
        orderRequestDto.setProducts(productIds);
        orderRequestDto.setFinishedAt(finishedTime);
        orderRequestDto.setUserId(userId);

        orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setUserId(userId);
        orderDto.setStatus(OrderStatus.APPROVED);
        orderDto.setProductIds(productIds);
        orderDto.setCreatedAt(createdTime);
        orderDto.setFinishedAt(finishedTime);

    }

    // PLACE_EVENT
    @Test
    void placeOrder_Positive_Scenario_1() throws Exception {
        doNothing().when(producerService).sendOrderPlacedEvent(event);
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/events/placed")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(producerService, times(1)).sendOrderPlacedEvent(event);
    }

    @Test
    void placeOrder_JsonIsEmpty_2() throws Exception {
        doNothing().when(producerService).sendOrderPlacedEvent(event);
        String json = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/events/placed")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
        verify(producerService, never()).sendOrderPlacedEvent(event);
    }

    // CREATE ORDER
    @Test
    void createOrder_PositiveScenario_1() throws Exception {
        // GIVEN
        String json = objectMapper.writeValueAsString(orderRequestDto);

        // WHEN  // THEN
        when(orderService.createOrder(orderRequestDto)).thenReturn(orderDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(orderDto.getOrderId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(orderDto.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productIds").value(hasItems(10,20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productIds").value(hasSize(greaterThan(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(orderDto.getStatus().toString()));

    }

}