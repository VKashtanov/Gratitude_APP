package ru.kashtanov.news_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.news_service.enums.ContentEnumType;
import ru.kashtanov.news_service.model.join.NewsContent;

import java.time.Instant;
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

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stored_file_name")
    private String storedFileName;

    @Column(name = "content_info")
    private String info;

    @Column(name = "contentType")
    private ContentEnumType contentType;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name="creation_date")
    private Instant creationDate;

    @Column(name = "size")
    private Long size;

    @OneToMany(mappedBy = "content")
    private List<NewsContent> content;

}
