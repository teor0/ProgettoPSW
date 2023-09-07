package application.repositories;

import application.entities.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Product> findById(Long id);
    Optional<Product> findByBarCode(String barCode);
    Boolean existsByBarCode(String barCode);
    List<Product> findByNameContaining(String name);
    List<Product> findAllByCategory(String category);
    List<Product> findByPriceBetween(double min, double max);
    List<Product> findByPriceGreaterThanEqual(double price);
    List<Product> findByPriceIsLessThanEqual(double price);

}
