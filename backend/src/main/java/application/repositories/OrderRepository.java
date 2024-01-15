package application.repositories;

import java.util.*;
import application.entities.Order;
import application.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(Utente u);
    List<Order> findByUserAndStatus(Utente u,String status);


}
