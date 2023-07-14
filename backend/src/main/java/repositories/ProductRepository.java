package repositories;

import entities.Category;
import entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByBarCode(String barCode);
    Boolean existsByBarCode(String barCode);
    List<Product> findByNameContaining(String name);
    List<Product> findAllByCategory(Category category);
    List<Product> findByPriceBetween(double min, double max);
    List<Product> findByPriceGreaterThanEqual(double price);
    List<Product> findByPriceIsLessThanEqual(double price);
    List<Product> findByQuantityGreaterThan(int quantity);


}
