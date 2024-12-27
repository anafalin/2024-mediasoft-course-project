package team.mediasoft.warehouse.datagenerator.category;

import team.mediasoft.warehouse.datagenerator.RandomService;
import team.mediasoft.warehouse.model.Category;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryEntityBuilder {
    private Category category = new Category();

    public static CategoryEntityBuilder aEntity() {
        return new CategoryEntityBuilder();
    }

    public static CategoryEntityBuilder aDefaultEntity() {
        CategoryEntityBuilder builder = new CategoryEntityBuilder();
        builder.category = Category.builder()
                .id(UUID.randomUUID())
                .name("Category Test_Name " + RandomService.getRandomInt())
                .products(new ArrayList<>())
                .build();
        return builder;
    }

    public Category build() {
        return category;
    }


    public CategoryEntityBuilder id(String id) {
        category.setId(UUID.fromString(id));
        return this;
    }

    public CategoryEntityBuilder name(String name) {
        category.setName(name);
        return this;
    }
}