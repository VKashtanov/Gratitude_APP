package ru.kashtanov.news_service.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Table(name = "news_content", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"news_id", "content_id"})
})
public class NewsContent {
    @Id
    private Long id;

    // Relationship fields
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;
}
