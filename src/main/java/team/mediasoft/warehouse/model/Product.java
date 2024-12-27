package team.mediasoft.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", length = 255, nullable = false, unique = true, updatable = false)
    private String name;

    @Column(name = "article_number", length = 10, nullable = false, unique = true)
    private String articleNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price = new BigDecimal(0);

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = new BigDecimal(0);

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @UpdateTimestamp
    @Column(name = "date_time_last_edit", nullable = false)
    private LocalDateTime dateTimeLastEdit;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDate dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public void setCategory(Category category) {
        category.getProducts().add(this);
        this.category = category;
    }
}