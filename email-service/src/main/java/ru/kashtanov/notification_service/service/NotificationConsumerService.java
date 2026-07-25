package ru.kashtanov.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.internals.Topic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kashtanov.notification_service.constant.TopicConstant;
import ru.kashtanov.notification_service.dto.OrderPlacedEvent;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Service
public class NotificationConsumerService {

    @KafkaListener(topics = TopicConstant.NOTIFICATION_TOPIC)
    public void handleOrderPlaced(String event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OrderPlacedEvent orderPlacedEvent = mapper.readValue(event, OrderPlacedEvent.class);
            System.out.println("OrderPlacedEvent: " + orderPlacedEvent);

            log.info("📩 Получено событие: {}", event);
        } catch (JacksonException e) {
            System.err.println("Could not deserialize order placed event: " + e.getMessage());
        }

    }
}
