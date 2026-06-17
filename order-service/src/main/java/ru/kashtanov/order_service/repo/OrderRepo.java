package ru.kashtanov.order_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kashtanov.order_service.model.Order;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
