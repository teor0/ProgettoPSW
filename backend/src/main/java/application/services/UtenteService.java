package application.services;
import application.entities.Utente;
import application.support.dto.UtenteDTO;
import application.support.exceptions.UtenteNotExistsException;
import application.support.exceptions.MailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import application.repositories.UtenteRepository;

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
    public void addUtente(Utente u) throws MailAlreadyExistsException {
        if(repo.existsByEmail(u.getEmail()))
            throw new MailAlreadyExistsException();
        repo.save(u);
    }
    @Transactional
    public boolean delete(UtenteDTO dto) throws UtenteNotExistsException {
        if(!repo.existsById(dto.getId()))
            throw new UtenteNotExistsException();
        repo.deleteById(dto.getId());
        return true;
    }


}
