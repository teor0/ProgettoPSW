package application.controller.rest;

import application.entities.OrderProducts;
import application.services.OrderProductService;
import application.support.dto.OrderProductsDTO;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.OrderProductsNotExistsException;
import application.support.exceptions.ProductNotExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orderproduct")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;


    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody OrderProductsDTO dto){
        try{
            orderProductService.createOrderProduct(dto);
        }
        catch (OrderNotExistsException e) {
            return new ResponseEntity<>("Order doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        catch(ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order product created", HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"order"})
    public ResponseEntity readOrderProductByOrder(@RequestParam(value = "order") Long id){
        List<OrderProducts> op;
        try{
            op=orderProductService.showByOrder(id);
        }
        catch(OrderNotExistsException e){
            return new ResponseEntity<>("This order doesn't exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(op,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity readOrderProduct(@PathVariable(value = "id") Long id){
        OrderProducts op;
        try{
            op=orderProductService.readOrderProducts(id);
        }
        catch(OrderProductsNotExistsException e){
            return new ResponseEntity<>("This order doesn't exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(op,HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"product"})
    public ResponseEntity readOrderProductByProduct(@RequestParam(value = "product") String barcode){
        List<OrderProducts> op;
        try {
            op=orderProductService.showByProduct(barcode);
        }
        catch(ProductNotExistsException e) {
            return new ResponseEntity<>("Product doesn't exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(op,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateOrderProduct(@RequestBody OrderProductsDTO dto){
        try {
            orderProductService.updateQta(dto);
        } catch (OrderProductsNotExistsException e) {
            return new ResponseEntity<>("Order product doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderproduct}")
    public ResponseEntity deleteOrderProduct(@PathVariable(value = "orderproduct") Long id){
        try{
            orderProductService.deleteOrderProduct(id);
        }
        catch(OrderProductsNotExistsException e){
            return new ResponseEntity<>("Order product doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        catch(OrderNotExistsException e){
            return new ResponseEntity<>("The order doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order product deleted", HttpStatus.OK);
    }

}
