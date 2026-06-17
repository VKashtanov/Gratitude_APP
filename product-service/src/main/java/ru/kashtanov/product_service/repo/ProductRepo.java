package ru.kashtanov.product_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kashtanov.product_service.model.Product;

import java.util.List;

/**
 * @author Viktor Кashtanov
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product WHERE product_id IN :ids",nativeQuery = true)
    List<Product> findAllByIdIn(@Param("ids") List<Long> ids);
//    List<Product> findAllByIdIn(List<Long> ids);
}
