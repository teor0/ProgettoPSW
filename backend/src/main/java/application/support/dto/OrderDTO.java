package application.support.dto;


import application.entities.Order;
import application.entities.OrderProducts;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private List<OrderProductsDTO> orderProducts;

    public OrderDTO(){
        this.id=0L;
        this.userId =0L;
        this.total=0.0;
        this.createDate=null;
        this.status="Pending";
        this.orderProducts=new ArrayList<>();
    }

    public OrderDTO(Order o){
        this.id=o.getId();
        this.userId =o.getUser().getId();
        this.total=o.getTotal();
        this.createDate=o.getCreateDate();
        this.status=o.getStatus();
        this.orderProducts=new ArrayList<>();
        for(OrderProducts op : o.getOrderProducts())
            this.orderProducts.add(new OrderProductsDTO((op)));
    }

}
