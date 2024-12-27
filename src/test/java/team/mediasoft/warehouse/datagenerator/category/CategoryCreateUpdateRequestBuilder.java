package team.mediasoft.warehouse.datagenerator.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.mediasoft.warehouse.datagenerator.RandomService;
import team.mediasoft.warehouse.dto.category.CategoryCreateUpdateRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateUpdateRequestBuilder {
    private CategoryCreateUpdateRequest dto = new CategoryCreateUpdateRequest();

    public static CategoryCreateUpdateRequestBuilder aDto() {
        return new CategoryCreateUpdateRequestBuilder();
    }

    public static CategoryCreateUpdateRequestBuilder aDefaultDto() {
        CategoryCreateUpdateRequestBuilder builder = new CategoryCreateUpdateRequestBuilder();
        builder.dto = CategoryCreateUpdateRequest.builder()
                .name("Category with new Test_Name " + RandomService.getRandomInt())
                .build();
        return builder;
    }

    public CategoryCreateUpdateRequest build() {
        return dto;
    }

    public CategoryCreateUpdateRequestBuilder name(String name) {
        dto.setName(name);
        return this;
    }
}