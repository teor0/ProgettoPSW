package application.services;

import application.entities.Order;
import application.entities.OrderProduct;
import application.entities.Product;
import application.repositories.OrderProductRepository;
import application.repositories.ProductRepository;
import application.support.dto.OrderDTO;
import application.support.dto.ProductDTO;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.OrderProductAlreadyExistsException;
import application.support.exceptions.ProductNotExistsException;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    @Transactional
    public void addOrderProduct(OrderProduct op) throws OrderProductAlreadyExistsException {
        if(repo.existsByOrderAndProduct(op.getOrder(),op.getProduct()))
            throw new OrderProductAlreadyExistsException();
        repo.save(op);
    }

    @Transactional(readOnly = true)
    public List<OrderProduct> showByOrder(OrderDTO dto) throws OrderNotExistsException {
        if(!orepo.existsById(dto.getId()))
            throw new OrderNotExistsException();
        Order o = orepo.findById(dto.getId()).get();
        return repo.findByOrder(o);
    }

    @Transactional(readOnly = true)
    public List<OrderProduct> showByProduct(ProductDTO dto) throws ProductNotExistsException {
        if(!prepo.existsByBarCode(dto.getBarCode()))
            throw new ProductNotExistsException();
        Product p = prepo.findByBarCode(dto.getBarCode()).get();
        return repo.findByProduct(p);
    }


}
