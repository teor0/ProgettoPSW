package application.services;

import application.entities.Order;
import application.entities.OrderProducts;
import application.entities.Product;
import application.repositories.OrderProductRepository;
import application.repositories.OrderRepository;
import application.repositories.ProductRepository;
import application.support.dto.ProductDTO;
import application.support.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prrepo;
    @Autowired
    private OrderProductRepository oprepo;
    @Autowired
    private OrderRepository orepo;

    @Transactional
    public void addProduct(Product p) throws ProductAlreadyExistsException {
        if(prrepo.existsByBarCode(p.getBarCode()))
            throw new ProductAlreadyExistsException();
        prrepo.save(p);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteProduct(Long id) throws ProductNotExistsException {
        if(!prrepo.existsById(id))
            throw new ProductNotExistsException();
        // il warning di get è gestito subito tramite existsById
        Product p=prrepo.findById(id).get();
        prrepo.delete(p);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<Product> getProduct(Long id) throws ProductNotExistsException {
        if(!prrepo.existsById(id))
            throw new ProductNotExistsException();
        return prrepo.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateProductName(ProductDTO dto) throws ProductNotExistsException{
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product oldProduct = prrepo.findById(dto.getId()).get();
        oldProduct.setName(dto.getName());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateProductDesc(ProductDTO dto) throws ProductNotExistsException{
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product oldProduct = prrepo.findById(dto.getId()).get();
        oldProduct.setDescription(dto.getDescription());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateProductCategory(ProductDTO dto) throws ProductNotExistsException{
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product oldProduct = prrepo.findById(dto.getId()).get();
        oldProduct.setCategory(dto.getCategory());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updatePrice(ProductDTO dto) throws ProductNotExistsException, OrderNotExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product p = prrepo.findById(dto.getId()).get();
        p.setPrice(dto.getPrice());
        List<OrderProducts> op= oprepo.findByProduct(p);
        for(OrderProducts o: op) {
            if(!orepo.existsById(o.getOrder().getId()))
                throw new OrderNotExistsException();
            Order ord= orepo.findById(o.getOrder().getId()).get();
            ord.setTotal(ord.getTotal()-(o.getPrice()*o.getQuantity()));
            o.setPrice(p.getPrice());
            ord.setTotal(ord.getTotal()+(o.getPrice()*o.getQuantity()));
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateQuantity(ProductDTO dto) throws ProductNotExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product p = prrepo.findById(dto.getId()).get();
        p.setQuantity(dto.getQuantity());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateBarCode(ProductDTO dto) throws ProductAlreadyExistsException, ProductNotExistsException {
        if(prrepo.existsByBarCode(dto.getBarCode()))
            throw new ProductAlreadyExistsException();
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        Product p = prrepo.findById(dto.getId()).get();
        p.setBarCode(dto.getBarCode());
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(){
        return prrepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> showProductByBarCode(String barCode) throws ProductNotExistsException {
        if(!prrepo.existsByBarCode(barCode))
            throw new ProductNotExistsException();
        return prrepo.findByBarCode(barCode);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByName(String name){
        return prrepo.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByCategory(String category){
        return prrepo.findAllByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByPriceBetween(double p1, double p2) throws UncorrectPriceException {
        if(p1>p2)
            throw new UncorrectPriceException();
        else if (p1<=0)
            throw new UncorrectPriceException();
        else if (p2<=0)
            throw new UncorrectPriceException();
        return prrepo.findByPriceBetween(p1,p2);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByPriceGreaterEqual(double p) throws  UncorrectPriceException{
        if(p<=0)
            throw new UncorrectPriceException();
        return prrepo.findByPriceGreaterThanEqual(p);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByPriceLessEqual(double p) throws UncorrectPriceException{
        if(p<=0)
            throw new UncorrectPriceException();
        return prrepo.findByPriceIsLessThanEqual(p);
    }
}
