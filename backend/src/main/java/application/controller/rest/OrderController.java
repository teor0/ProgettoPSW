package application.controller.rest;

import application.entities.Order;
import application.services.OrderService;
import application.support.dto.OrderDTO;
import application.support.exceptions.OrderAlreadyExistsException;
import application.support.exceptions.OrderNotExistsException;
import application.support.exceptions.UtenteNotExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@Valid @RequestBody Order o){
        try{
            orderService.addOrder(o);
        }
        catch(OrderAlreadyExistsException e){
            return new ResponseEntity<>("Order already exists!", HttpStatus.BAD_REQUEST);
        }
        catch(UtenteNotExistsException ex){
            return new ResponseEntity<>("User doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order added", HttpStatus.OK);
    }


    @GetMapping(value = "/search", params = {"user"})
    public ResponseEntity readOrderByUser(@RequestParam(value = "user") Long id){
        List<Order> o;
        try{
            o=orderService.showByUser(id);
        }
        catch (UtenteNotExistsException e){
            return new ResponseEntity<>("User doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"from","to"})
    public ResponseEntity readOrderByDate(@RequestParam(value = "from") LocalDate d1,
                                          @RequestParam(value = "to") LocalDate d2){
        return new ResponseEntity<>(orderService.showByDate(d1,d2),HttpStatus.OK);
    }

    @GetMapping(value = "/search", params={"status"})
    public ResponseEntity readOrderByStatus(@RequestParam(value = "status") String status){
        return new ResponseEntity(orderService.showByStatus(status),HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity updateOrder(@Valid @RequestBody OrderDTO dto){
        try{
            orderService.updateOrder(dto);
        }
        catch(OrderNotExistsException e){
            return new ResponseEntity<>("Order doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{order}")
    public ResponseEntity deleteOrder(@PathVariable(value = "order") Long id){
        try {
            orderService.deleteOrder(id);
        }
        catch(OrderNotExistsException e){
            return new ResponseEntity<>("Order doesn't exists!", HttpStatus.BAD_REQUEST);
        }
        catch (UtenteNotExistsException ex){
            return new ResponseEntity<>("The user no longer exists!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order deleted",HttpStatus.OK);
    }
}
