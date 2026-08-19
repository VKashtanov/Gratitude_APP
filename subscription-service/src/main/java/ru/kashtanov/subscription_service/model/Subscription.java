package ru.kashtanov.subscription_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumStatus;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(generator = "subscription_factory")
    @SequenceGenerator(name = "subscription_factory", sequenceName = "subscription_id_factory")
    private Long id;
    private String userId;
    private String targetId;
    private String type;
    private SubscriptionEnumStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant expiresAt;

}
