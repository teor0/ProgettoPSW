package application.support.dto;


import application.entities.Utente;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class UtenteDTO {

    private Long id;
    private String name;
    private String email;

    public UtenteDTO(){
        this.id=0L;
        this.name=null;
        this.email=null;
    }

    public UtenteDTO(Utente u){
        this.id= u.getId();
        this.name=u.getName();
        this.email=u.getEmail();
    }


}
