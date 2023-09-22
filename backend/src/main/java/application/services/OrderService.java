package application.services;

import application.entities.Order;
import application.entities.OrderProducts;
import application.entities.Utente;
import application.repositories.OrderProductRepository;
import application.repositories.OrderRepository;
import application.support.dto.OrderDTO;
import application.support.exceptions.OrderAlreadyExistsException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.UtenteNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import application.repositories.UtenteRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService{

    @Autowired
    private OrderRepository repo;
    @Autowired
    private OrderProductRepository oprepo;
    @Autowired
    private UtenteRepository urepo;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addOrder(Order o) throws OrderAlreadyExistsException, UtenteNotExistsException {
        if(repo.existsById(o.getId()))
            throw new OrderAlreadyExistsException();
        if(!urepo.existsById(o.getUser().getId()))
            throw new UtenteNotExistsException();
        repo.save(o);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteOrder(Long id) throws OrderNotExistsException, UtenteNotExistsException {
        if(!repo.existsById(id))
            throw new OrderNotExistsException();
        Order o = repo.findById(id).get();
        List<OrderProducts> ops = oprepo.findByOrder(o);
        oprepo.deleteAll(ops);
        if(!urepo.existsById(o.getUser().getId()))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(o.getUser().getId()).get();
        u.getOrders().remove(o);
        repo.delete(o);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateOrder(OrderDTO dto) throws OrderNotExistsException {
        if(!repo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = repo.findById(dto.getId()).get();
        o.setTotal(dto.getTotal());
        o.setCreateDate(dto.getCreateDate());
    }

    @Transactional(readOnly = true)
    public List<Order> showByDate(LocalDate d1, LocalDate d2){
       return repo.findByCreateDateBetween(d1,d2);
    }

    @Transactional(readOnly = true)
    public List<Order> showByStatus(String status){
        return repo.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Order> showByUserAndStatus(Long id,String status) throws UtenteNotExistsException {
        if(!urepo.existsById(id))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(id).get();
        return repo.findByUserAndStatus(u,status);
    }

    @Transactional(readOnly = true)
    public List<Order> showByUser(Long id) throws UtenteNotExistsException {
        if(!urepo.existsById(id))
            throw new UtenteNotExistsException();
        Utente u = urepo.findById(id).get();
        return repo.findByUser(u);
    }


}
