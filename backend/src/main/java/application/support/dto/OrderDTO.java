package application.support.dto;


import application.entities.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@ToString
@Getter
@Setter
@EqualsAndHashCode
public class OrderDTO{

    private Long id;
    private Long user;
    private Double total;
    private LocalDate createDate;

    public OrderDTO(){
        this.id=0L;
        this.user=0L;
        this.total=0.0;
        this.createDate=null;
    }

    public OrderDTO(Order o){
        this.id=o.getId();
        this.user=o.getUser().getId();
        this.total=o.getTotal();
        this.createDate=o.getCreateDate();
    }

}
