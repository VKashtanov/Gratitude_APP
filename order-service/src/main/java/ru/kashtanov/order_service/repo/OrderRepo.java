package ru.kashtanov.order_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.order_service.model.Order;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE order_id IN :ids", nativeQuery = true)
    List<Order> findSpecifiedOrders(@Param("ids") List<Long> ids);
}
