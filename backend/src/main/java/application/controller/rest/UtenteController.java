package application.controller.rest;

import application.entities.Utente;
import application.services.UtenteService;
import application.support.exceptions.MailAlreadyExistsException;
import application.support.exceptions.UtenteNotExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("")
    public ResponseEntity create(@Valid @RequestBody Utente u){
        try{
            utenteService.addUtente(u);
        }
        catch (MailAlreadyExistsException e) {
            return new ResponseEntity<>("Errore mail già in uso", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Registrazione con successo", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody Utente read(@PathVariable(value = "id") Long id){
        Utente u=null;
        try{
            u=utenteService.getUtente(id);
        }
        catch(UtenteNotExistsException e){
            return u;
        }
        return u;
    }

    @GetMapping("/{name}")
    public @ResponseBody List<Utente> readByName(@PathVariable(value = "name") String name){
        return utenteService.getUtenteByName(name);
    }

    @GetMapping("/{email}")
    public @ResponseBody Optional<Utente> readByEmail(@PathVariable(value = "email") String email){
        return utenteService.getUtenteByEmail(email);
    }

    @GetMapping("/all")
    public ResponseEntity readAll(){
        return new ResponseEntity(utenteService.getUtenti(),HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity readOrders(@RequestParam(value= "id") Long id){
        Utente u=null;
        try{
            u=utenteService.getUtente(id);
        }
        catch(UtenteNotExistsException e){
            return new ResponseEntity("Errore nessun utente",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u.getOrders(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value= "id") Long id ){
        try {
            utenteService.delete(id);
        }
        catch(UtenteNotExistsException e){
            return new ResponseEntity<>("Utente non esistente", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cancellazione effettuata con successo", HttpStatus.OK);
    }


}
