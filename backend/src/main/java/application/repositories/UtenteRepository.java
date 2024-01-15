package application.repositories;

import application.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Long> {


    Optional<Utente> findByEmail(String email);
    List<Utente> findByUsernameContaining(String username);
    Optional<Utente> findByUsername(String username);
    List<Utente> findByNameContaining(String name);
    Boolean existsByEmail(String email);
}
