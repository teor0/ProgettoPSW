package application.repositories;

import application.entities.Order;
import application.entities.OrderProducts;
import application.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderProductRepository extends JpaRepository<OrderProducts,Long> {

    List<OrderProducts> findByProduct(Product p);
    List<OrderProducts> findByOrder(Order o);



}
