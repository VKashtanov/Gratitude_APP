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
@Table(name = "comment",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","target_id","entity_type"})
})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_factory")
    @SequenceGenerator(name = "comment_factory", sequenceName = "comment_id_factory", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "entity_type", nullable = false)
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
