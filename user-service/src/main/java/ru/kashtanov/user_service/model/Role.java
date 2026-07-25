package ru.kashtanov.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_factory")
    @SequenceGenerator(name = "roles_id_factory",sequenceName = "roles_factory", allocationSize = 1)
    private Long roleId;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<UsersRoles> usersRoles;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
