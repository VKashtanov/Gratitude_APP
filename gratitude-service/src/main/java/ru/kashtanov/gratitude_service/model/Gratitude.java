package ru.kashtanov.gratitude_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "gratitude_id")
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "timestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant timestamp;

    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size=100)
    @CollectionTable(name = "recipient_users",
                   joinColumns = @JoinColumn(name = "gratitude_id"))
    @Column(name = "recipient_id")
    private List<Long> recipientIds;



}
