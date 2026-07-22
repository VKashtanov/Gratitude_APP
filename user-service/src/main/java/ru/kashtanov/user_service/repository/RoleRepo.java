package ru.kashtanov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.user_service.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles WHERE role_name = :roleName", nativeQuery = true)
    Optional<Role> findByRoleName(@Param("roleName") String roleName);
}
