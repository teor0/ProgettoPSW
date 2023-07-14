package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.OrderProduct;
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
