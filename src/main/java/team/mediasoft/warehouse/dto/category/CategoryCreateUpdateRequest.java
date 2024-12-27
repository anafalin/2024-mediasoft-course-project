package team.mediasoft.warehouse.dto.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateUpdateRequest {
    @NotNull(message = "Name can not be null")
    @NotEmpty(message = "Name can not be empty")
    private String name;
}