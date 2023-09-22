package application.controller.rest;

import application.entities.Product;
import application.services.ProductService;
import application.support.dto.ProductDTO;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.ProductAlreadyExistsException;
import application.support.exceptions.ProductNotExistsException;
import application.support.exceptions.UncorrectPriceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity createProduct(@Valid @RequestBody Product p){
        try {
            productService.addProduct(p);
        }
        catch(ProductAlreadyExistsException e){
            return new ResponseEntity<>("Product already exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"barcode"})
    public ResponseEntity readProduct(@RequestParam(value = "barcode") String barcode){
        Optional<Product> p;
        try{
            p=productService.showProductByBarCode(barcode);
        }
        catch(ProductNotExistsException e){
            return new ResponseEntity<>("Error product not found",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity readProducts(){
        return new ResponseEntity<>(productService.showAllProducts(),HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity readProductByName(@RequestParam(value = "name") String name){
        return new ResponseEntity<>(productService.showProductByName(name),HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"category"})
    public ResponseEntity readProductsByCategory(@RequestParam(value = "category") String category){
        return new ResponseEntity<>(productService.showProductByCategory(category),HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"min","max"})
    public ResponseEntity readProductsByPriceBetween(@RequestParam(value = "min") double p1,
                                                     @RequestParam(value = "max") double p2){
        List<Product> p;
        try{
            p=productService.showProductByPriceBetween(p1,p2);
        }
        catch(UncorrectPriceException e){
            return new ResponseEntity<>("Uncorrect price", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"priceGreaterEq"})
    public ResponseEntity readProductsByPriceGreaterEqual(@RequestParam(value = "priceGreaterEq") double price){
        List<Product> p;
        try{
            p=productService.showProductByPriceGreaterEqual(price);
        }
        catch(UncorrectPriceException e){
            return new ResponseEntity<>("Uncorrect price", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"priceLessEq"})
    public ResponseEntity readProductsByPriceLessEqual(@RequestParam(value = "priceLessEq") double price){
        List<Product> p;
        try{
            p=productService.showProductByPriceLessEqual(price);
        }
        catch(UncorrectPriceException e){
            return new ResponseEntity<>("Uncorrect price", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity readProductsPaged(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                            @RequestParam(value = "size", defaultValue = "15") int size,
                                            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        List<Product> result= productService.showAllProducts(pageNumber,size,sortBy);
        if(result.isEmpty())
            return new ResponseEntity<>("No results!",HttpStatus.OK);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/update/name")
    public ResponseEntity updateProductName(@Valid @RequestBody ProductDTO p){
        try{
            productService.updateProductName(p);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @PutMapping("/update/description")
    public ResponseEntity updateProductDescription(@Valid @RequestBody ProductDTO p){
        try{
            productService.updateProductDesc(p);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @PutMapping("/update/category")
    public ResponseEntity updateProductCategory(@Valid @RequestBody ProductDTO p){
        try{
            productService.updateProductCategory(p);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @PutMapping("/update/price")
    public ResponseEntity updatePrice(@Valid @RequestBody ProductDTO p){
        try{
            productService.updatePrice(p);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException ex) {
            return new ResponseEntity<>("Order doesn't exists",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @PutMapping("/update/quantity")
    public ResponseEntity updateQuantity(@Valid @RequestBody ProductDTO p){
        try{
            productService.updateQuantity(p);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @PutMapping("/update/barcode")
    public ResponseEntity updateBarCode(@Valid @RequestBody ProductDTO p){
        try{
            productService.updateBarCode(p);
        }
        catch(ProductAlreadyExistsException ex) {
            return new ResponseEntity<>("Product already updated", HttpStatus.BAD_REQUEST);
        }
        catch (ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{product}")
    public ResponseEntity deleteProduct(@PathVariable(value="product") Long id){
        boolean flag= false;
        try{
           flag=productService.deleteProduct(id);
        }
        catch(ProductNotExistsException e){
            return new ResponseEntity<>("Product doesn't exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flag,HttpStatus.OK);
    }
}
