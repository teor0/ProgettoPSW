package application.support.dto;

import application.entities.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode
public class ProductDTO {

    private Long id;
    private String barCode;
    private String name;
    private String category;
    private Double price;
    private String description;
    private Integer quantity;

    public ProductDTO(){
        this.id=0L;
        this.barCode=null;
        this.name=null;
        this.category=null;
        this.price=0.00;
        this.description=null;
        this.quantity=0;
    }

    public ProductDTO(Product p){
        this.id=p.getId();
        this.barCode=p.getBarCode();
        this.name=p.getName();
        this.category=p.getCategory();
        this.price=p.getPrice();
        this.description=p.getDescription();
        this.quantity=p.getQuantity();
    }

}
