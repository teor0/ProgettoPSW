package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Size(max = 15)
    @NotNull
    @Column(name = "bar code")
    private String barCode;

    @Basic
    @Size(max = 30)
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "category")
    private Category category;

    @Basic
    @NotNull
    @Column(name = "price")
    private Double price;

    @Basic
    @Size(max = 200)
    @Column(name = "description")
    @ToString.Exclude
    @JsonIgnore
    private String description;

    @Basic
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Version
    @NotNull
    @Column(name = "version")
    @JsonIgnore
    private long version;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

}