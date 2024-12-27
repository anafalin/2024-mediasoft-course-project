package team.mediasoft.warehouse.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import team.mediasoft.warehouse.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductFullResponse {
    private UUID id;

    private String name;

    private BigDecimal price;

    private String articleNumber;

    private String description;

    private BigDecimal quantity;

    private Boolean isAvailable;

    private CategoryResponse category;

    private LocalDateTime dateTimeLastEdit;

    private LocalDate dateCreation;
}

