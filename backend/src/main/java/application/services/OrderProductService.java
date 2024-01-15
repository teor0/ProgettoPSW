package application.services;

import application.entities.Order;
import application.entities.OrderProducts;
import application.entities.Product;
import application.repositories.OrderProductRepository;
import application.repositories.ProductRepository;
import application.support.dto.OrderProductsDTO;
import application.support.exceptions.OrderHandledException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.OrderProductsNotExistsException;
import application.support.exceptions.ProductNotExistsException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import application.repositories.OrderRepository;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository repo;
    @Autowired
    private OrderRepository orepo;
    @Autowired
    private ProductRepository prepo;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {OrderHandledException.class,ProductNotExistsException.class}, propagation = Propagation.REQUIRED)
    public void createOrderProduct(OrderProductsDTO dto) throws OrderNotExistsException, ProductNotExistsException, OrderHandledException {
        if(!orepo.existsById(dto.getOrderId()))
            throw new OrderNotExistsException();
        if(!prepo.existsById(dto.getProductId()))
            throw new ProductNotExistsException();
        OrderProducts op= new OrderProducts();
        op.setQuantity(dto.getQuantity());
        Order o= orepo.findById(dto.getOrderId()).get();
        if(o.getStatus()=="Handled")
            throw new OrderHandledException();
        Product p= prepo.findById(dto.getProductId()).get();
        op.setOrder(o);
        op.setProduct(p);
        op.setPrice(p.getPrice());
        o.setTotal(o.getTotal()+(p.getPrice()*op.getQuantity()));
        repo.save(op);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateQta(OrderProductsDTO dto) throws OrderProductsNotExistsException{
        if(!repo.existsById(dto.getId()))
            throw new OrderProductsNotExistsException();
        OrderProducts op= repo.findById(dto.getId()).get();
        Order o = orepo.findById(op.getOrder().getId()).get();
        o.setTotal(o.getTotal()-(op.getPrice()*op.getQuantity()));
        op.setQuantity(dto.getQuantity());
        o.setTotal(o.getTotal()+(op.getPrice()*op.getQuantity()));
    }

    @Transactional(readOnly = true)
    public OrderProductsDTO readOrderProducts(Long id)throws OrderProductsNotExistsException{
        if(!repo.existsById(id))
            throw new OrderProductsNotExistsException();
        OrderProducts op=repo.findById(id).get();
        return new OrderProductsDTO(op);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteOrderProduct(Long id) throws OrderProductsNotExistsException, OrderNotExistsException {
        if(!repo.existsById(id))
            throw new OrderProductsNotExistsException();
        OrderProducts op= repo.findById(id).get();
        if(!orepo.existsById(op.getOrder().getId()))
            throw new OrderNotExistsException();
        Order o= orepo.findById(op.getOrder().getId()).get();
        o.setTotal(o.getTotal()-(op.getPrice()* op.getQuantity()));
        o.getOrderProducts().remove(op);
        repo.delete(op);
    }


    @Transactional(readOnly = true)
    public List<OrderProductsDTO> showByOrder(Long id) throws OrderNotExistsException {
        if(!orepo.existsById(id))
            throw new OrderNotExistsException();
        Order o = orepo.findById(id).get();
        List<OrderProducts> l=repo.findByOrder(o);
        List<OrderProductsDTO> ret= new ArrayList<>();
        for(OrderProducts op: l){
            ret.add(new OrderProductsDTO(op));
        }
        return ret;
    }
}
