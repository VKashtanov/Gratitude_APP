package ru.kashtanov.comment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.comment_service.enums.EntityType;

/**
 * @author Viktor Кashtanov
 */

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_factory")
    @SequenceGenerator(name = "comment_factory", sequenceName = "comment_id_factory", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name = "comment")
    private String comment;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "user_id")
    private Long user_id;
}
