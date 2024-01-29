package application.controller.rest;

import application.entities.Order;
import application.services.OrderService;
import application.support.dto.OrderDTO;
import application.support.exceptions.OrderAlreadyExistsException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.UtenteNotExistsException;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/order")
@PreAuthorize(value="hasRole('ROLE_User')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<JSONObject> createOrder(@Valid @RequestBody Order o){
        JSONObject response= new JSONObject();
        try{
            orderService.addOrder(o);
        }
        catch(OrderAlreadyExistsException e){
            response.put("msg","Order already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch(UtenteNotExistsException ex){
            response.put("msg","User doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Order created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/search", params = {"user"})
    public ResponseEntity readOrderByUser(@RequestParam(value = "user") Long id){
        List<OrderDTO> o;
        try{
            o=orderService.showByUser(id);
        }
        catch (UtenteNotExistsException e){
            JSONObject response= new JSONObject();
            response.put("msg","User doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"user","status"})
    public ResponseEntity readOrderByUserAndStatus(@RequestParam(value= "user") Long id, @RequestParam(value = "status") String status){
        List<OrderDTO> o;
        try{
            o=orderService.showByUserAndStatus(id, status);
        }
        catch (UtenteNotExistsException e) {
            return new ResponseEntity<>("User doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(o, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JSONObject> deleteOrder(@PathVariable(value = "id") Long id){
        JSONObject response= new JSONObject();
        try {
            orderService.deleteOrder(id);
        }
        catch(OrderNotExistsException e){
            response.put("msg","Order doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (UtenteNotExistsException ex){
            response.put("msg","The user no longer exists!");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Order deleted");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
