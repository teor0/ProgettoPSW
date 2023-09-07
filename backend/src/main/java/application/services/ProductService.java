package application.services;
import application.entities.Product;
import application.repositories.OrderProductRepository;
import application.repositories.ProductRepository;
import application.support.dto.ProductDTO;
import application.support.exceptions.ProductAlreadyExistsException;
import application.support.exceptions.ProductNotExistsException;
import application.support.exceptions.UncorrectPriceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prrepo;
    @Autowired
    private OrderProductRepository oprepo;

    @Transactional
    public void addProduct(Product p) throws ProductAlreadyExistsException {
        if(!prrepo.existsByBarCode(p.getBarCode()))
            throw new ProductAlreadyExistsException();
        prrepo.save(p);
    }

    @Transactional
    public boolean deleteProduct(ProductDTO dto) throws ProductNotExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        // il warning di get è gestito subito tramite existsById
        Product p=prrepo.findById(dto.getId()).get();
        oprepo.deleteAll(oprepo.findByProduct(p)); //codice possibilmente fuorviante in quanto esiste CASCADE.REMOVE
        prrepo.delete(p);
        return true;
    }

    @Transactional
    public void updateProduct(ProductDTO dto) throws ProductNotExistsException, ProductAlreadyExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        if(prrepo.existsByBarCode(dto.getBarCode()))
            throw new ProductAlreadyExistsException();
        String upBarcode = dto.getBarCode();
        String upName = dto.getName();
        String upCat = dto.getCategory();
        String upDesc = dto.getDescription();
        int upQuantity = dto.getQuantity();
        double upPrice = dto.getPrice();
        Product oldProduct = prrepo.findById(dto.getId()).get();
        oldProduct.setBarCode(upBarcode);
        oldProduct.setName(upName);
        oldProduct.setCategory(upCat);
        oldProduct.setDescription(upDesc);
        oldProduct.setQuantity(upQuantity);
        oldProduct.setPrice(upPrice);
        prrepo.flush();
    }

    @Transactional
    public void updatePrice(ProductDTO dto) throws ProductNotExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        double upPrice = dto.getPrice();
        Product p = prrepo.findById(dto.getId()).get();
        p.setPrice(upPrice);
        prrepo.flush();
    }

    @Transactional
    public void updateQuantity(ProductDTO dto) throws ProductNotExistsException {
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        int upQuantity = dto.getQuantity();
        Product p = prrepo.findById(dto.getId()).get();
        p.setQuantity(upQuantity);
        prrepo.flush();
    }

    @Transactional
    public void updateBarCode(ProductDTO dto) throws ProductAlreadyExistsException, ProductNotExistsException {
        if(prrepo.existsByBarCode(dto.getBarCode()))
            throw new ProductAlreadyExistsException();
        if(!prrepo.existsById(dto.getId()))
            throw new ProductNotExistsException();
        String upBarCode = dto.getBarCode();
        Product p = prrepo.findById(dto.getId()).get();
        p.setBarCode(upBarCode);
        prrepo.flush();
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(){
        return prrepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(int pageNumber, int size, String sortBy){
        Pageable paging = PageRequest.of(pageNumber, size, Sort.by(sortBy));
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
