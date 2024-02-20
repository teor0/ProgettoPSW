package application.services;

import application.entities.*;
import application.repositories.*;
import application.support.dto.CartDTO;
import application.support.dto.OrderDTO;
import application.support.dto.OrderProductsDTO;
import application.support.dto.UtenteDTO;
import application.support.exceptions.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
    @Autowired
    private OrderService orderService;


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
    public CartDTO setCart(OrderDTO dto) throws OrderNotExistsException, UtenteNotExistsException {
        if(!orepo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = orepo.findById(dto.getId()).get();
        if(!urepo.existsById(dto.getUserId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getUserId()).get();
        Cart c= repo.findByUser(u).get();
        c.setOrder(o);
        return new CartDTO(c);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CartDTO clearCart(Long userId) throws UtenteNotExistsException, CartNotExistsException, OrderNotExistsException {
        if(!urepo.existsById(userId))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(userId).get();
        if(!repo.existsByUser(u))
            throw new CartNotExistsException();
        Cart c = repo.findByUser(u).get();
        if(c.getOrder()==null){
            throw new OrderNotExistsException();
        }
        if(!orepo.existsById(c.getOrder().getId()))
            throw new OrderNotExistsException();
        orderService.deleteOrder(c.getOrder().getId());
        c.setOrder(null);
        repo.flush();
        return new CartDTO(c);
    }

    @Transactional(readOnly = true)
    public CartDTO getCart(Long id) throws UtenteNotExistsException, CartNotExistsException {
        if(!urepo.existsById(id))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(id).get();
        if(!repo.existsByUser(u))
            throw new CartNotExistsException();
        return new CartDTO(repo.findByUser(u).get());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {UtenteNotExistsException.class, QtaUnvaliableException.class, PriceChangedException.class}, propagation = Propagation.REQUIRED)
    public void acquire(OrderDTO dto) throws QtaUnvaliableException, PriceChangedException, OrderNotExistsException, UtenteNotExistsException {
        if(!urepo.existsById(dto.getUserId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(dto.getUserId()).get();
        if(!orepo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = orepo.findById(dto.getId()).get();
        List <OrderProductsDTO> items = dto.getOrderProducts();
        Cart c = repo.findByUser(u).get();
        for(OrderProductsDTO item: items){
            Product p= prepo.findById(item.getProductId()).get();
            if(p.getQuantity()<item.getQuantity())
                throw new QtaUnvaliableException();
            if(!p.getPrice().equals(item.getPrice()))
                throw new PriceChangedException();
            p.setQuantity(p.getQuantity()- item.getQuantity());
            prepo.save(p);
        }
        //payment...
        o.setStatus("Handled");
        c.setOrder(null);
        repo.save(c);
    }
}
