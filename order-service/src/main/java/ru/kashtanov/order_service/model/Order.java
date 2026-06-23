package ru.kashtanov.order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import ru.kashtanov.order_service.enums.OrderStatus;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_factory")
    @SequenceGenerator(name = "order_factory", sequenceName = "order_id_factory", allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)// nullable means in jpa / it gets checked upon action with db (insert,update..)
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "chosen_products",
            joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "finished_at")
    private Instant finishedAt;


}
