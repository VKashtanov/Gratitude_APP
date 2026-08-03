package ru.kashtanov.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.kashtanov.order_service.dto.OrderPlacedEvent;
import ru.kashtanov.order_service.enums.TopicConstant;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class OrderProducerService {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderProducerService(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent) {
        // We warrant that all messages from here will be put into the same partition
        // If we remove the key, it ensures Robin-Ribbon spreading upon all partitions evenly
        kafkaTemplate.send(TopicConstant.NOTIFICATION_TOPIC.getValue(), orderPlacedEvent)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Got response at  "+ Timestamp.from(Instant.now()) +", result: " + result );
                        log.info("result: {}", result);
                    } else {;
                        log.error("Something went wrong upon sending even :" + exception.getMessage());

                    }
                });
    }
}
