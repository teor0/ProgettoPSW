package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import static jakarta.persistence.FetchType.EAGER;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    //annotazione che forse con il front end non servira
    @JsonBackReference(value = "user-orders")
    private Utente user;

    @Basic
    @NotNull
    @Column(name = "total")
    private Double total;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;

    @Basic
    @Column(name = "status")
    private String status="Pending";

    @ToString.Exclude
    @OneToMany(fetch = EAGER,mappedBy = "order",cascade = {CascadeType.REMOVE,CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference(value = "orderproducts")
    private List<OrderProducts> orderProducts;

    @ToString.Exclude
    @OneToOne(mappedBy = "order")
    @JsonBackReference(value = "cart")
    @JsonIgnore
    private Cart cart;

}