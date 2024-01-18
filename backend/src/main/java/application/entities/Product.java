package application.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;
import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Size(max = 15)
    @NotNull
    @Column(name = "bar_code")
    private String barCode;

    @Basic
    @Size(max = 30)
    @NotNull
    @Column(name = "name")
    private String name;

    @Basic
    @Size(max = 30)
    @NotNull
    @Column(name = "category")
    private String category;

    @Basic
    @NotNull
    @Column(name = "price")
    private Double price;

    @Basic
    @Size(max = 200)
    @Column(name = "description")
    @ToString.Exclude
    private String description;

    @Basic
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Version
    @JsonIgnore
    @Column(name = "version", nullable = false)
    private Long version;


    @ToString.Exclude
    @JsonIgnore
    @OneToMany(fetch = LAZY,mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JsonManagedReference(value = "product")
    private List<OrderProducts> orderProducts;


}