package team.mediasoft.warehouse.datagenerator.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.datagenerator.RandomService;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseBuilder {
    private CategoryResponse dto = new CategoryResponse();

    public static CategoryResponseBuilder aDto() {
        return new CategoryResponseBuilder();
    }

    public static CategoryResponseBuilder aDefaultDto() {
        CategoryResponseBuilder builder = new CategoryResponseBuilder();
        builder.dto = CategoryResponse.builder()
                .name("Category with new Test_Name " + RandomService.getRandomInt())
                .build();
        return builder;
    }

    public CategoryResponse build() {
        return dto;
    }

    public CategoryResponseBuilder name(String name) {
        dto.setName(name);
        return this;
    }
}