package ru.kashtanov.news_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.news_service.enums.ContentEnumType;
import ru.kashtanov.news_service.model.join.NewsContent;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Setter
@Getter
@Table(name="content")
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "content_factory")
    @SequenceGenerator(name = "content_factory", sequenceName = "content_id_factory", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content_info")
    private String info;

    @Column(name = "contentType")
    private ContentEnumType contentType;

    @Column(name = "creator_id")
    private Long creatorId;

    @OneToMany(mappedBy = "content")
    private List<NewsContent> content;

}
