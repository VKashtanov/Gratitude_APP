package ru.kashtanov.order_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.kashtanov.order_service.dto.OrderPlacedEvent;
import ru.kashtanov.order_service.enums.TopicConstant;

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
        kafkaTemplate.send(TopicConstant.NOTIFICATION_TOPIC.getValue(), orderPlacedEvent)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.error("Something went wrong upon sending even :" + exception.getMessage());
                    } else {
                        log.info("Sent order placed event");
                        log.info("result: {}", result);
                    }
                });
    }
}
