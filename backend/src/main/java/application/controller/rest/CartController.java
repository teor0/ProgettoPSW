package application.controller.rest;

import application.entities.Cart;
import application.services.CartService;
import application.support.dto.OrderDTO;
import application.support.dto.UtenteDTO;
import application.support.exceptions.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/show/{user}")
    public ResponseEntity readCart(@PathVariable(value = "user") Long id){
        Cart c;
        try{
            c=cartService.getCart(id);
        }
        catch(UtenteNotExistsException e){
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        catch(CartNotExistsException ex) {
            return new ResponseEntity<>("Cart not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c,HttpStatus.OK);
    }

    @PostMapping("/acquire")
    public ResponseEntity acquireCart(@Valid @RequestBody UtenteDTO dto){
        try {
            cartService.acquire(dto);
        } catch (UtenteNotExistsException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException e) {
            return new ResponseEntity<>("Order not exists!", HttpStatus.BAD_REQUEST);
        } catch (QtaUnvaliableException e) {
            return new ResponseEntity<>("The product quantity no longer available!", HttpStatus.BAD_REQUEST);
        } catch (PriceChangedException e) {
            return new ResponseEntity<>("Price of the product changed!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity createCart(@Valid @RequestBody UtenteDTO dto){
        try {
            cartService.createCart(dto);
        } catch (CartAlreadyExistsException e) {
            return new ResponseEntity<>("Cart already exists!", HttpStatus.BAD_REQUEST);
        } catch (UtenteNotExistsException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cart created!", HttpStatus.OK);
    }

    @PutMapping("/checkout")
    public ResponseEntity updateCart(@Valid @RequestBody OrderDTO dto){
        Cart c;
        try {
            c=cartService.setCart(dto);
        } catch (UtenteNotExistsException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        } catch (OrderNotExistsException e) {
            return new ResponseEntity<>("Order not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{user}")
    public ResponseEntity deleteCart(@PathVariable(value = "user") Long id){
        Cart c;
        try{
            c=cartService.clearCart(id);
        }
        catch (OrderNotExistsException e) {
            return new ResponseEntity<>("Order not found!", HttpStatus.BAD_REQUEST);
        } catch (CartNotExistsException e) {
            return new ResponseEntity<>("Cart not found!", HttpStatus.BAD_REQUEST);
        } catch (UtenteNotExistsException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c, HttpStatus.OK);
    }


}
