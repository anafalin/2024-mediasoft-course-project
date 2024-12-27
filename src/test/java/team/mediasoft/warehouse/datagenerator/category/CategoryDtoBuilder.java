package team.mediasoft.warehouse.datagenerator.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoBuilder {
    private CategoryDto dto = new CategoryDto();

    public static CategoryDtoBuilder aDto() {
        return new CategoryDtoBuilder();
    }

    public static CategoryDtoBuilder aDefaultDto() {
        CategoryDtoBuilder builder = new CategoryDtoBuilder();
        builder.dto = CategoryDto.builder()
                .id(UUID.randomUUID())
                .name("Category with new Test_Name " + RandomService.getRandomInt())
                .build();
        return builder;
    }

    public CategoryDto build() {
        return dto;
    }

    public CategoryDtoBuilder name(String name) {
        dto.setName(name);
        return this;
    }
}