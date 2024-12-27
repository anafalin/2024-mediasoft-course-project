package team.mediasoft.warehouse.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString(exclude = {"products"})
@EqualsAndHashCode(exclude = {"products"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", length = 20, nullable = false, unique = true, updatable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST})
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = products;
        for (Product product : products) {
            product.setCategory(this);
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }
}