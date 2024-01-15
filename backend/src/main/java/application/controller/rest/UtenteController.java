package application.controller.rest;

import application.entities.Utente;
import application.services.UtenteService;
import application.support.exceptions.AuthenticationException;
import application.support.exceptions.UtenteNotExistsException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = {"https://localhost:4200"})
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PreAuthorize(value="hasRole('ROLE_User')")
    @GetMapping(value="/search/{id}")
    public @ResponseBody Optional<Utente> readById(@PathVariable(value = "id") Long id) throws UtenteNotExistsException {
        return utenteService.getUtente(id);
    }

    @PreAuthorize(value="hasRole('ROLE_Admin')")
    @GetMapping(value="/search", params = "name")
    public @ResponseBody List<Utente> readByName(@RequestParam(value = "name") String name){
        return utenteService.getUtentiByName(name);
    }

    @PreAuthorize(value="hasRole('ROLE_Admin')")
    @GetMapping(value="/search",params ="email")
    public @ResponseBody Optional<Utente> readByEmail(@RequestParam(value = "email") String email){
        return utenteService.getUtenteByEmail(email);
    }

    @PreAuthorize(value="hasRole('ROLE_User')")
    @GetMapping(value="/{username}")
    public @ResponseBody Optional<Utente> readByUsername(@PathVariable(value = "username") String username){
        return utenteService.getUtenteByUsername(username);
    }

    @PreAuthorize(value="hasRole('ROLE_Admin')")
    @GetMapping(value="/search", params = "username")
    public @ResponseBody List<Utente> readAllByUsername(@RequestParam String username){
        return utenteService.getUtentiByUsername(username);
    }

    @PreAuthorize(value="hasRole('ROLE_Admin')")
    @GetMapping("/all")
    public @ResponseBody List<Utente> readAll(){
        return utenteService.getUtenti();
    }

    @PreAuthorize(value="hasRole('ROLE_User')")
    @GetMapping(value="/orders")
    public ResponseEntity readOrders(@RequestParam(value= "id") Long id){
        Utente u;
        try{
            u=utenteService.getUtente(id).get();
        }
        catch(UtenteNotExistsException e){
            return new ResponseEntity("Error no order found!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u.getOrders(),HttpStatus.OK);
    }

    @PreAuthorize(value="hasRole('ROLE_Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> delete(@PathVariable(value= "id") Long id ){
        JSONObject response= new JSONObject();
        try {
            utenteService.delete(id);
        }
        catch(UtenteNotExistsException e){
            response.put("msg","User doesn't exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch(AuthenticationException e){
            response.put("msg","Error during delete process");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","User deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
