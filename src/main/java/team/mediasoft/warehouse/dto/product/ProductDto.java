package team.mediasoft.warehouse.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID uuid;

    private String name;

    private BigDecimal price;

    private String articleNumber;

    private String description;

    private BigDecimal quantity;

    private String category;

    private Boolean isAvailable;
}