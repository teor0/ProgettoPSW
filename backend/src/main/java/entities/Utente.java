package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;
import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "utente")
public class Utente {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "user",cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = LAZY,mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Cart cart;


}