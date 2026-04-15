package ru.kashtanov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.user_service.model.User;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
