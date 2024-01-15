package application.repositories;

import application.entities.Cart;
import application.entities.Utente;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Cart> findByUser(Utente utente);
    boolean existsByUser(Utente utente);
}
