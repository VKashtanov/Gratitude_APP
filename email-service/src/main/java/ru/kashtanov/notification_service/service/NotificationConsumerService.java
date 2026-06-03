package ru.kashtanov.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kashtanov.notification_service.dto.OrderPlacedEvent;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Service
public class NotificationConsumerService {

    @KafkaListener(topics = "notification_topic")
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("📩 Получено событие: {}", event);

        System.out.println("Заказ №" + event.getOrderId() + " создан!");
        System.out.println("Email пользователя: " + event.getUserEmail());
        System.out.println("Сумма: " + event.getOrderPrice());
    }
}
