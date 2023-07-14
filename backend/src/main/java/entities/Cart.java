package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private Utente user;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

}