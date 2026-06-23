package ru.kashtanov.gratitude_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.gratitude_service.model.Gratitude;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface GratitudeRepo extends JpaRepository<Gratitude, Long> {
}
