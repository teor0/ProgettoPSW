package application.controller.rest;

import application.services.CartService;
import application.support.dto.CartDTO;
import application.support.dto.OrderDTO;
import application.support.dto.UtenteDTO;
import application.support.exceptions.*;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/cart")
@PreAuthorize(value="hasRole('ROLE_User')")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<JSONObject> createCart(@Valid @RequestBody UtenteDTO dto){
        JSONObject response= new JSONObject();
        try {
            cartService.createCart(dto);
        } catch (CartAlreadyExistsException e) {
            response.put("msg","Cart already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UtenteNotExistsException e) {
            response.put("msg","User not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Cart created!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/refresh")
    public ResponseEntity updateCart(@Valid @RequestBody OrderDTO dto){
        CartDTO c;
        JSONObject response= new JSONObject();
        try {
            c=cartService.setCart(dto);
        } catch (UtenteNotExistsException e) {
            response.put("msg","User not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException e) {
            response.put("msg","Order not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c, HttpStatus.OK);
    }


    @GetMapping("/show/{user}")
    public @ResponseBody CartDTO readCart(@PathVariable(value = "user") Long id){
        CartDTO c;
        try{
            c=cartService.getCart(id);
        }
        catch(UtenteNotExistsException | CartNotExistsException e){
            return new CartDTO();
        }
        return c;
    }

    @PostMapping("/checkout")
    public ResponseEntity<JSONObject> acquireCart(@Valid @RequestBody OrderDTO dto){
        JSONObject response= new JSONObject();
        try {
            cartService.acquire(dto);
        } catch (UtenteNotExistsException e) {
            response.put("msg","User not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException e) {
            response.put("msg","Order not exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (QtaUnvaliableException e) {
            response.put("msg","The product quantity no longer available!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (PriceChangedException e) {
            response.put("msg","Price of the product changed!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Acquire done!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{user}")
    public ResponseEntity deleteCart(@PathVariable(value = "user") Long id){
        CartDTO c;
        JSONObject response= new JSONObject();
        try{
            c=cartService.clearCart(id);
        }
        catch (OrderNotExistsException e) {
            response.put("msg","Order not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (CartNotExistsException e) {
            response.put("msg","Cart not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UtenteNotExistsException e) {
            response.put("msg","User not found!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c, HttpStatus.OK);
    }


}
