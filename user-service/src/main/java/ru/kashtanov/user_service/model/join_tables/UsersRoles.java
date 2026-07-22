package ru.kashtanov.user_service.model.join_tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.model.User;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Table(name = "users_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class UsersRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_factory")
    @SequenceGenerator(name = "user_roles_factory", sequenceName = "user_roles_factory_id", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
