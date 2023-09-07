package application.repositories;

import application.entities.Cart;
import application.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(Utente utente);
    boolean existsByUser(Utente utente);
}
