package application.repositories;

import application.entities.Order;
import application.entities.OrderProduct;
import application.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    List<OrderProduct> findByProduct(Product p);
    List<OrderProduct> findByOrder(Order o);

    boolean existsByOrderAndProduct(Order o, Product p);


}
