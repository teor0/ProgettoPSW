package application.services;

import application.entities.Cart;
import application.entities.Utente;
import application.repositories.CartRepository;
import application.repositories.OrderRepository;
import application.repositories.UtenteRepository;
import application.support.dto.UtenteDTO;
import application.support.exceptions.CartNotExistsException;
import application.support.exceptions.UtenteNotExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repo;
    @Autowired
    private UtenteRepository urepo;
    @Autowired
    private OrderRepository orepo;

    @Transactional
    public Cart clearCart(UtenteDTO dto) throws UtenteNotExistsException, CartNotExistsException {
        if(!urepo.existsById(dto.getId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getId()).get();
        if(!repo.existsByUser(u))
            throw new CartNotExistsException();
        Cart c = repo.findByUser(u).get();
        orepo.delete(c.getOrder());
        repo.flush();
        return repo.findByUser(u).get();
    }



}
