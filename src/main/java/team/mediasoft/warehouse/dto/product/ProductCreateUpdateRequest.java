package team.mediasoft.warehouse.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateUpdateRequest {
    @NotNull(message = "Name can not be null")
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotNull(message = "Article number can not be null")
    @NotEmpty(message = "Article number can not be empty")
    @Length(min = 1, max = 10, message = "Min length of article number is 1 and max is 10")
    private String articleNumber;

    @NotBlank(message = "Description can not be null, empty and must contains more then one word")
    private String description;

    @DecimalMin(value = "10", message = "Min price is 10")
    private BigDecimal price = new BigDecimal(0);

    @DecimalMin(value = "0", message = "Min quantity is 0")
    private BigDecimal quantity = new BigDecimal(0);

    @NotNull(message = "Category can not be null")
    @NotEmpty(message = "Category can not be empty")
    private String category;
}