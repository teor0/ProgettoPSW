package application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    private Utente user;

    @Basic
    @NotNull
    @Column(name = "total")
    private Double total;

    @Basic
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "create_date")
    private LocalDate createDate;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();
}