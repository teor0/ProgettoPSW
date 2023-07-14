package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.Cart;
import entities.Utente;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUtente(Utente utente);
    boolean existsByUtente(Utente utente);
}
