package ru.kashtanov.subscription_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumStatus;
import ru.kashtanov.subscription_service.enums.SubscriptionEnumType;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@Table(name = "subscriptions", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "target_id"})})
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(generator = "subscription_factory")
    @SequenceGenerator(name = "subscription_factory", sequenceName = "subscription_id_factory", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SubscriptionEnumType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SubscriptionEnumStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Version
    private Long version;

}
