package ru.kashtanov.notification_service.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.internals.Topic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.kashtanov.notification_service.constant.TopicConstant;
import ru.kashtanov.notification_service.dto.OrderPlacedEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Service
public class NotificationConsumerService {

    private final ObjectMapper mapper;

    public NotificationConsumerService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = TopicConstant.NOTIFICATION_TOPIC)
    public void handleOrderPlaced(List<String> events, Acknowledgment ack) {
        try {
            log.info("Thread name: " + Thread.currentThread().getName());
            for (String event : events) {
                OrderPlacedEvent orderPlacedEvent = mapper.readValue(event, OrderPlacedEvent.class);
                log.info("Received order placed event: " + orderPlacedEvent);
                log.info("📩 Получено событие: {}", event);
                log.info("Before" + Timestamp.from(Instant.now()));

                Thread.sleep(100); // processing emulation
                ack.acknowledge();
                log.info("After" + Timestamp.from(Instant.now()));

            }
        } catch (JacksonException e) {
            System.err.println("Could not deserialize order placed event: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
