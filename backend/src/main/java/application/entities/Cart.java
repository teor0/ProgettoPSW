package application.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JsonManagedReference(value = "user")
    @JoinColumn(name = "user_id")
    private Utente user;

    @OneToOne
    @JsonManagedReference(value = "cart")
    //@JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    @Column(name = "version")
    @Version
    private Long version;

}