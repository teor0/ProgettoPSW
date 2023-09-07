package application.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "\"orderProducts\"")
public class OrderProduct {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Basic
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

}