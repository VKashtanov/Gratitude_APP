package ru.kashtanov.news_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_factory")
    @SequenceGenerator(name = "news_factory", sequenceName = "news_id_factory", allocationSize = 1)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private Long author;

    @ElementCollection
    @CollectionTable(name = "mentioned_users", // create a new table to store values
            joinColumns = @JoinColumn(name = "news_id")) // creates "news_id" column (foreign key)
    @Column(name = "user_id") // creates "user_id" column (stores the values)
    private List<Long> mentioned_users; //




}