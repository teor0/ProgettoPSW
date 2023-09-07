package application.services;

import application.entities.Order;
import application.entities.OrderProduct;
import application.entities.Utente;
import application.repositories.OrderProductRepository;
import application.repositories.OrderRepository;
import application.support.dto.OrderDTO;
import application.support.dto.UtenteDTO;
import application.support.exceptions.OrderAlreadyExistsException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.ProductNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    @Transactional
    public void createOrder(Order o) throws OrderAlreadyExistsException {
        if(repo.existsById(o.getId()))
            throw new OrderAlreadyExistsException();
        repo.save(o);
    }

    @Transactional
    public void deleteOrder(OrderDTO dto) throws OrderNotExistsException {
        if(!repo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = repo.findById(dto.getId()).get();
        List<OrderProduct> ops = oprepo.findByOrder(o);
        oprepo.deleteAll(ops);
        repo.delete(o);
    }

    @Transactional
    public void updateOrder(OrderDTO dto) throws OrderNotExistsException {
        if(!repo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = repo.findById(dto.getId()).get();
        o.setTotal(dto.getTotal());
        o.setCreateDate(dto.getCreateDate());
        repo.flush();
    }

    @Transactional(readOnly = true)
    public List<Order> showByDate(LocalDate d1, LocalDate d2){
       return repo.findByCreateDateBetween(d1,d2);
    }

    @Transactional(readOnly = true)
    public List<Order> showByUser(UtenteDTO dto) throws ProductNotExistsException {
        if(!urepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Utente u = urepo.findById(dto.getId()).get();
        return repo.findByUser(u);
    }





}
