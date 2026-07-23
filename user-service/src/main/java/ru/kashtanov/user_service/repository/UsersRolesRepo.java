package ru.kashtanov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;

import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UsersRolesRepo extends JpaRepository<UsersRoles, Long> {
//    SELECT * FROM users_roles WHERE role_id=2 AND user_id=38
    @Query(value = "SELECT * FROM users_roles WHERE role_id = :role_id AND user_id =:user_id" , nativeQuery = true)
    public Optional<UsersRoles> findByRoleIdAndUserId(@Param(value = "role_id") Long role_id,
                                                      @Param(value = "user_id") Long user_id);
}
