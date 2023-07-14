package services;
import exceptions.MailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.UtenteRepository;
import entities.Utente;
import java.util.*;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository repo;

    @Transactional(readOnly = true)
    public Utente getUtente(long id){
        return repo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtenti(){
        return repo.findAll();
    }

    @Transactional
    public long create(Utente u) throws MailAlreadyExistsException {
        if(repo.existsByEmail(u.getEmail()))
            throw new MailAlreadyExistsException();
        return repo.save(u).getId();
    }
    @Transactional
    public boolean delete(long id){
        repo.deleteById(id);
        return true;
    }


}
