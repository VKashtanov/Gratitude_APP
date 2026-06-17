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
//@Table(name = "users_todel")
//public class UserOk {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "users_todel_factory")
//    @SequenceGenerator(name = "users_todel_factory",sequenceName = "users_todel_id_factory", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;
//    @Column(name = "fistName")
//    private String fistName;
//    @Column(name = "lastName")
//    private String lastName;
//
//    // JPA relations
//    @OneToMany(mappedBy = "author")
//    private List<News> listNews;
//
//    @OneToMany(mappedBy = "userOk")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "mentioned_userOk")
//    private List<UserNews> mentionedUsers;
//
//
//
//
//
//
//
//
//}
