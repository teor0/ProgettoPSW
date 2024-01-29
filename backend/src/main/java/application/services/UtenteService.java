package application.services;

import application.entities.Utente;
import application.support.auth.KeycloakService;
import application.support.dto.UtenteDTO;
import application.support.exceptions.AuthenticationException;
import application.support.exceptions.UtenteNotExistsException;
import application.support.exceptions.MailAlreadyExistsException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import application.repositories.UtenteRepository;
import java.util.*;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository repo;
    @Autowired
    private KeycloakService keycloakService;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = AuthenticationException.class)
    public void addUtente(Utente u) throws MailAlreadyExistsException, AuthenticationException {
        if(repo.existsByEmail(u.getEmail()))
            throw new MailAlreadyExistsException();
        repo.save(u);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> getUtente(Long id) throws UtenteNotExistsException {
        if(!repo.existsById(id))
            throw new UtenteNotExistsException();
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtentiByName(String name){
        return repo.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtentiByUsername(String username){
        return repo.findByUsernameContaining(username);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> getUtenteByUsername(String username){
        return repo.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> getUtenteByEmail(String email){
        return repo.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtenti(){
        return repo.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = AuthenticationException.class)
    public void delete(Long id) throws UtenteNotExistsException, AuthenticationException {
        if(!repo.existsById(id))
            throw new UtenteNotExistsException();
        Utente u=repo.findById(id).get();
        if(!keycloakService.deleteUser(keycloakService.getToken(),u.getUsername())
                .isSameCodeAs(HttpStatus.NO_CONTENT))
            throw new AuthenticationException();
        repo.deleteById(id);
    }

}
