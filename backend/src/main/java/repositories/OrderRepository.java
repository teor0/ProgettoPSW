package repositories;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import entities.Order;
import entities.Utente;
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(Utente u);
    List<Order> findByCreateDateBetween(LocalDate d1, LocalDate d2);

}
