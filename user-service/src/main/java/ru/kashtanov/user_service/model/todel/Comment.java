package ru.kashtanov.user_service.model.todel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Viktor Кashtanov
 */

//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "comments_todel")
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_todel_factory")
//    @SequenceGenerator(name = "comments_todel_factory", sequenceName = "comments_todel_id_factory", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "comment")
//    private String comment;
//
//    // JPA relations
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "news_id")
//    private News news;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserOk userOk;
//}
