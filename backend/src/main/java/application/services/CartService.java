package application.services;

import application.entities.*;
import application.repositories.*;
import application.support.dto.OrderDTO;
import application.support.dto.UtenteDTO;
import application.support.exceptions.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repo;
    @Autowired
    private UtenteRepository urepo;
    @Autowired
    private OrderRepository orepo;
    @Autowired
    private ProductRepository prepo;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createCart(UtenteDTO dto) throws UtenteNotExistsException, CartAlreadyExistsException {
        if(!urepo.existsById(dto.getId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getId()).get();
        if(repo.existsByUser(u))
            throw new CartAlreadyExistsException();
        Cart c= new Cart();
        c.setUser(u);
        repo.save(c);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Cart clearCart(Long userId) throws UtenteNotExistsException, CartNotExistsException, OrderNotExistsException {
        if(!urepo.existsById(userId))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(userId).get();
        if(!repo.existsByUser(u))
            throw new CartNotExistsException();
        Cart c = repo.findByUser(u).get();
        if(!orepo.existsById(c.getOrder().getId()))
            throw new OrderNotExistsException();
        c.setOrder(null);
        repo.flush();
        return repo.findByUser(u).get();
    }

    @Transactional(readOnly = true)
    public Cart getCart(Long id) throws UtenteNotExistsException, CartNotExistsException{
        if(!urepo.existsById(id))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(id).get();
        if(!repo.existsByUser(u))
            throw new CartNotExistsException();
        return repo.findByUser(u).get();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Cart setCart(OrderDTO dto) throws OrderNotExistsException, UtenteNotExistsException {
        if(!orepo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = orepo.findById(dto.getId()).get();
        if(!urepo.existsById(dto.getUser()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getUser()).get();
        Cart c= repo.findByUser(u).get();
        c.setOrder(o);
        return c;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {UtenteNotExistsException.class, QtaUnvaliableException.class, PriceChangedException.class})
    public void acquire(UtenteDTO dto) throws QtaUnvaliableException, PriceChangedException, OrderNotExistsException, UtenteNotExistsException {
        if(!urepo.existsById(dto.getId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getId()).get();
        Cart c = repo.findByUser(u).get();
        if(!orepo.existsById(c.getOrder().getId()))
            throw new OrderNotExistsException();
        Order o = orepo.findById(c.getOrder().getId()).get();
        List <OrderProducts> items = o.getOrderProducts();
        for(OrderProducts item: items){
            Product p= prepo.findById(item.getProduct().getId()).get();
            if(p.getQuantity()<item.getQuantity())
                throw new QtaUnvaliableException();
            if(!p.getPrice().equals(item.getPrice()))
                throw new PriceChangedException();
            p.setQuantity(p.getQuantity()- item.getQuantity());
            prepo.save(p);
        }
        //pagamento...
        o.setStatus("Handled");
        c.setOrder(null);
        repo.save(c);
    }
}
