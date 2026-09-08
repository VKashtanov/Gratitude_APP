package ru.kashtanov.comment_service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kashtanov.comment_service.enums.TargetType;

import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_factory")
    @SequenceGenerator(name = "comment_factory", sequenceName = "comment_id_factory", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }


}
