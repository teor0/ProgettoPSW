package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "orderproducts")
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "order_id")
    @JsonBackReference(value = "orderproducts")
    private Order order;

    //forse devo usare la json managedreference
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "product")
    private Product product;

    @Basic
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Basic
    @NotNull
    @Column(name = "price")
    private Double price;

}