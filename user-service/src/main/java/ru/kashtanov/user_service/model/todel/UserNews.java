//package ru.kashtanov.user_service.model.todel;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
///**
// * @author Viktor Кashtanov
// */
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "user_news_todel",
//uniqueConstraints = {
//        @UniqueConstraint(columnNames ={"news_id","user_id"})
//})
//public class UserNews {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_news_todel_factory")
//    @SequenceGenerator(name = "user_news_todel_factory",sequenceName = "user_news_todel_id_factory", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "news_id")
//    private News mentioned_news;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserOk mentioned_userOk;
//}
