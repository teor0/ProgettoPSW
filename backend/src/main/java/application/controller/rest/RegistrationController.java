package application.controller.rest;

import application.entities.Utente;
import application.services.UtenteService;
import application.support.exceptions.AuthenticationException;
import application.support.exceptions.MailAlreadyExistsException;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4200/*" })
@RequestMapping("/Authentication")
public class RegistrationController {

    @Autowired
    private UtenteService utenteService;


    @PostMapping
    public ResponseEntity<JSONObject> create(@Valid @RequestBody Utente u){
        JSONObject response= new JSONObject();
        try{
            utenteService.addUtente(u);
        }
        catch (MailAlreadyExistsException e) {
            response.put("msg","This mail is already in use!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (AuthenticationException e){
            response.put("msg","Authentication error sorry");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("msg","Succesfully registered");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
