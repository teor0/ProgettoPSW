package application.controller.rest;


import application.services.OrderProductService;
import application.support.dto.OrderProductsDTO;
import application.support.exceptions.OrderHandledException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.OrderProductsNotExistsException;
import application.support.exceptions.ProductNotExistsException;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://localhost:4200"})
@RequestMapping("/order-product")
@PreAuthorize(value="hasRole('ROLE_User')")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;


    @PostMapping
    public ResponseEntity<JSONObject> create(@Valid @RequestBody OrderProductsDTO dto){
        JSONObject response= new JSONObject();
        try{
            orderProductService.createOrderProduct(dto);
        }
        catch(OrderNotExistsException e) {
            response.put("msg","Order doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch(ProductNotExistsException e) {
            response.put("msg","Product doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch(OrderHandledException e){
            response.put("msg","Unexpected order error!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Order product created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"order"})
    public ResponseEntity readOrderProductByOrder(@RequestParam(value = "order") Long id){
        List<OrderProductsDTO> op;
        try{
            op=orderProductService.showByOrder(id);
        }
        catch(OrderNotExistsException e){
            JSONObject response= new JSONObject();
            response.put("msg","This order doesn't exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(op,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity readOrderProduct(@PathVariable(value = "id") Long id){
        OrderProductsDTO op;
        try{
            op=orderProductService.readOrderProducts(id);
        }
        catch(OrderProductsNotExistsException e){
            JSONObject response= new JSONObject();
            response.put("msg","This order doesn't exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(op,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<JSONObject> updateOrderProduct(@RequestBody OrderProductsDTO dto){
        JSONObject response= new JSONObject();
        try {
            orderProductService.updateQta(dto);
        } catch (OrderProductsNotExistsException e) {
            response.put("msg", "Order product doesn't exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JSONObject> deleteOrderProduct(@PathVariable(value = "id") Long id){
        JSONObject response= new JSONObject();
        try{
            orderProductService.deleteOrderProduct(id);
        }
        catch(OrderProductsNotExistsException e){
            response.put("msg","Order product doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch(OrderNotExistsException e) {
            response.put("msg","Order doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Order product deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
