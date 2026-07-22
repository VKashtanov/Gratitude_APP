package ru.kashtanov.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username","email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_factory")
    @SequenceGenerator(name = "user_factory", sequenceName = "user_id_factory", allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

    // JWT authorization required fields
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    // Other fields
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "portrait_url")
    private String portraitUrl;


    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "user")
    private List<UsersRoles> usersRoles;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
