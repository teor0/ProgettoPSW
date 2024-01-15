package application.controller.rest;

import application.entities.Product;
import application.services.ProductService;
import application.support.dto.ProductDTO;
import application.support.exceptions.*;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"https://localhost:4200"})
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PostMapping
    public ResponseEntity<JSONObject> createProduct(@Valid @RequestBody Product p){
        JSONObject response= new JSONObject();
        try {
            productService.addProduct(p);
        }
        catch(ProductAlreadyExistsException e){
            response.put("msg","Product already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="/search/{id}")
    public @ResponseBody Optional<Product> readById(@PathVariable(value = "id") Long id) {
        Optional<Product> p;
        try{
            p=productService.getProduct(id);
        }
        catch(ProductNotExistsException e){
            return Optional.empty();
        }
        return p;
    }

    @GetMapping(value = "/search", params = {"barcode"})
    public @ResponseBody Optional<Product>readProduct(@RequestParam(value = "barcode") String barcode){
        Optional<Product> p;
        try{
            p=productService.showProductByBarCode(barcode);
        }
        catch(ProductNotExistsException e){
            return Optional.empty();
        }
        return p;
    }

    @GetMapping("/all")
    public @ResponseBody List<Product> readProducts(){
        return productService.showAllProducts();
    }

    @GetMapping(value = "/search", params = {"name"})
    public @ResponseBody List<Product> readProductByName(@RequestParam(value = "name") String name){
        return productService.showProductByName(name);
    }

    @GetMapping(value = "/search", params = {"category"})
    public @ResponseBody List<Product> readProductsByCategory(@RequestParam(value = "category") String category){
        return productService.showProductByCategory(category);
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


    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/name")
    public ResponseEntity<JSONObject> updateProductName(@Valid @RequestBody ProductDTO p){
        JSONObject response=new JSONObject();
        try{
            productService.updateProductName(p);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/description")
    public ResponseEntity<JSONObject> updateProductDescription(@Valid @RequestBody ProductDTO p){
        JSONObject response= new JSONObject();
        try{
            productService.updateProductDesc(p);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/category")
    public ResponseEntity<JSONObject> updateProductCategory(@Valid @RequestBody ProductDTO p){
        JSONObject response= new JSONObject();
        try{
            productService.updateProductCategory(p);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/price")
    public ResponseEntity<JSONObject> updatePrice(@Valid @RequestBody ProductDTO p){
        JSONObject response=new JSONObject();
        try{
            productService.updatePrice(p);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException ex) {
            response.put("msg","Order doesn't exists");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/quantity")
    public ResponseEntity<JSONObject> updateQuantity(@Valid @RequestBody ProductDTO p){
        JSONObject response=new JSONObject();
        try{
            productService.updateQuantity(p);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @PutMapping("/update/barCode")
    public ResponseEntity<JSONObject> updateBarCode(@Valid @RequestBody ProductDTO p){
        JSONObject response=new JSONObject();
        try{
            productService.updateBarCode(p);
        }
        catch(ProductAlreadyExistsException ex) {
            response.put("msg","Barcode already in use");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Vendor')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JSONObject> deleteProduct(@PathVariable(value="id") Long id){
        JSONObject response= new JSONObject();
        try{
           productService.deleteProduct(id);
        }
        catch(ProductNotExistsException e){
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Product deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
