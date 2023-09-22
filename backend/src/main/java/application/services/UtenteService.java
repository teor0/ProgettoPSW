package application.services;

import application.entities.Utente;
import application.support.exceptions.UtenteNotExistsException;
import application.support.exceptions.MailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import application.repositories.UtenteRepository;
import java.util.*;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository repo;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addUtente(Utente u) throws MailAlreadyExistsException {
        if(repo.existsByEmail(u.getEmail()))
            throw new MailAlreadyExistsException();
        repo.save(u);
    }

    @Transactional(readOnly = true)
    public Utente getUtente(Long id) throws UtenteNotExistsException {
        if(!repo.existsById(id))
            throw new UtenteNotExistsException();
        return repo.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtenteByName(String name){
        return repo.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> getUtenteByEmail(String email){
        return repo.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtenti(){
        return repo.findAll();
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) throws UtenteNotExistsException {
        if(!repo.existsById(id))
            throw new UtenteNotExistsException();
        repo.deleteById(id);
    }

}
