package application.support.dto;

import application.entities.OrderProducts;
import lombok.*;
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class OrderProductsDTO {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;

    public OrderProductsDTO(){
        this.id=0L;
        this.orderId=0L;
        this.productId=0L;
        this.price=0.0;
        this.quantity=0;
    }

    public OrderProductsDTO(OrderProducts op){
        this.id=op.getId();
        this.orderId=op.getOrder().getId();
        this.productId=op.getProduct().getId();
        this.quantity=op.getQuantity();
        this.price=op.getPrice();
    }

}