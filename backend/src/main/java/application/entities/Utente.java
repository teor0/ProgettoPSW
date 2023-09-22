package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Size(max = 30)
    @NotNull
    @Column(name = "name")
    private String name;

    @Basic
    @Size(max = 50)
    @NotNull
    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "user",cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference(value = "user-orders")
    @JsonIgnore
    private List<Order> orders;

    @ToString.Exclude
    @JsonBackReference(value = "user")
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Cart cart;

}