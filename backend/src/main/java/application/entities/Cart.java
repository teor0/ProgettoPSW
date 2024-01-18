package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NotNull
    @JsonManagedReference(value = "user")
    @JoinColumn(name = "user_id")
    @OneToOne
    private Utente user;

    @JsonManagedReference(value = "order")
    @JoinColumn(name = "order_id")
    @OneToOne
    private Order order;

    @NotNull
    @Column(name = "version")
    @JsonIgnore
    @Version
    private Long version;

}