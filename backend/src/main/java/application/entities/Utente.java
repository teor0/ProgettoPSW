package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Size(max=15)
    @NotNull
    @Column(name = "username")
    private String username;

    @Basic
    @Size(max = 50)
    @NotNull
    @Column(name = "name")
    private String name;

    @Basic
    @Size(max = 50)
    @NotNull
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "role")
    private String role;

    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "user",cascade = {CascadeType.MERGE})
    @JsonManagedReference(value = "user-orders")
    @JsonIgnore
    private List<Order> orders;

    @ToString.Exclude
    @JsonBackReference(value = "user")
    @OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private Cart cart;

}