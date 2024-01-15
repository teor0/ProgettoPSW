package application.support.dto;


import application.entities.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;


@ToString
@Getter
@Setter
@EqualsAndHashCode
public class OrderDTO{

    private Long id;
    private Long userId;
    private Double total;
    private Date createDate;
    private String status;

    public OrderDTO(){
        this.id=0L;
        this.userId =0L;
        this.total=0.0;
        this.createDate=null;
        this.status="Pending";
    }

    public OrderDTO(Order o){
        this.id=o.getId();
        this.userId =o.getUser().getId();
        this.total=o.getTotal();
        this.createDate=o.getCreateDate();
        this.status=o.getStatus();
    }

}
