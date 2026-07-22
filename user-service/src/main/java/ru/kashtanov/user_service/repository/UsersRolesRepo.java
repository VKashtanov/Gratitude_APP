package ru.kashtanov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UsersRolesRepo extends JpaRepository<UsersRoles, Long> {

    @Query(value = "SELECT * FROM users_roles WHERE role_id = :roleId AND user_id =:userId" , nativeQuery = true)
    public UsersRoles findByRoleIdAndUserId(@Param(value = "roleId") Long roleId, @Param(value = "userId") Long userId);
}
