package ru.kashtanov.gratitude_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gratitude {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gratitude_factory")
    @SequenceGenerator(name = "gratitude_factory", sequenceName = "gratitude_id_factory")
    @Column(name = "id")
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", columnDefinition = "BIGINT")
    private Long timestamp;

}
