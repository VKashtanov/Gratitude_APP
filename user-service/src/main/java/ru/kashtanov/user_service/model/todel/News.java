//package ru.kashtanov.user_service.model.todel;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
///**
// * @author Viktor Кashtanov
// */
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "news_todel")
//public class News {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "news_todel_factory")
//    @SequenceGenerator(name = "news_todel_factory",sequenceName = "news_todel_id_factory", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "title")
//    private String title;
//
//    // JPA relations
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserOk author;
//
//    @OneToMany(mappedBy = "news")
//    // MIND IT
//    // 0. it has own pk id
//    // 1. Table News says: go to UserNews table
//    // 2. Check the field "news" it must be linked and related to me, check if it has my fk
//    // 3. Take from there foreign key (news_id) and map it with mine id (news.id)
//    // 4. It binds news PK id, with foregin key fk.
//    // and now entities work simultaneously into both sides
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "mentioned_news")
//    private List<UserNews> mentionedNews;
//
//
//
//}
