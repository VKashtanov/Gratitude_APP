package ru.kashtanov.news_service.model.join;

import jakarta.persistence.*;
import ru.kashtanov.news_service.model.Content;
import ru.kashtanov.news_service.model.News;

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
