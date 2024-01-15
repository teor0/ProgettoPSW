package application.support.dto;

import application.entities.Cart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CartDTO {

    private Long id;
    private Long userId;
    private Long orderId;

    public CartDTO(){
        this.id=0L;
        this.userId=0L;
        this.orderId=0L;
    }

    public CartDTO(Cart c){
        this.id=c.getId();
        this.userId=c.getUser().getId();
        if(c.getOrder()!=null)
            this.orderId=c.getOrder().getId();
        else
            this.orderId=null;
    }

}
