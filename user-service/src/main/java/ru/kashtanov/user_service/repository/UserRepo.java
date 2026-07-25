package ru.kashtanov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.user_service.model.User;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username" , nativeQuery = true)
    Optional<User> findByUsername(@Param(value ="username" ) String username);
}
