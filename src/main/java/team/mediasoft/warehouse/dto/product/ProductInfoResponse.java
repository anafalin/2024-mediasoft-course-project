package team.mediasoft.warehouse.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.mediasoft.warehouse.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponse {
    private String name;

    private BigDecimal price;

    private String articleNumber;

    private String description;

    private BigDecimal quantity;

    private LocalDateTime dateTimeLastEdit;

    private LocalDate dateCreation;

    private CategoryResponse category;

    private Boolean isAvailable;
}