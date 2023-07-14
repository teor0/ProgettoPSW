package entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic
    @NotNull
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = LAZY,mappedBy = "category",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Product> products = new LinkedHashSet<>();

}