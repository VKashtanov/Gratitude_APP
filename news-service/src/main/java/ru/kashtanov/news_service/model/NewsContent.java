package ru.kashtanov.news_service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.kashtanov.news_service.enums.NewsContentEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@Table(name = "news_content")
@NoArgsConstructor
@AllArgsConstructor
public class NewsContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_content_factory")
    @SequenceGenerator(name = "news_content_factory", sequenceName = "news_content_id_factory")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private NewsContentEnum type;

    @Column( scale = 2)
    private BigDecimal balance;

    @Column(name = "additional", unique = true)
    private String additional;

    @OneToOne(mappedBy = "newsContent")
    private News news;

    @Version
    private Long version;

}
