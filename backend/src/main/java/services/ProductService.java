package services;

import entities.Product;
import exceptions.ProductAlreadyExistsException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ProductRepository;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prrepo;
    @Autowired
    private EntityManager em;

    @Transactional
    public void addProduct(Product p) throws ProductAlreadyExistsException{
        if(!prrepo.existsByBarCode(p.getBarCode()))
            throw new ProductAlreadyExistsException();
        prrepo.save(p);
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(){
        return prrepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(int pageNumber, int size, String sortBy){
        PageRequest paging = PageRequest.of(pageNumber, size, Sort.by(sortBy));
        Page<Product> pagedResult= prrepo.findAll(paging);
        if(pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public Optional<Product> showProductByBarCode(String barCode){
        return prrepo.findByBarCode(barCode);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductByName(String name){
        return prrepo.findByNameContaining(name);
    }



}
