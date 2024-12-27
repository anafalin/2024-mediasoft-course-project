package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.dto.product.ProductResponse;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponseBuilder {
    private ProductResponse dto = new ProductResponse();

    public static ProductResponseBuilder aDto() {
        return new ProductResponseBuilder();
    }

    public static ProductResponseBuilder aDefaultDto() {
        ProductResponseBuilder builder = new ProductResponseBuilder();
        builder.dto = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .category(TestObjectData.aCategoryResponse().getDto())
                .build();
        return builder;
    }

    public ProductResponse build() {
        return dto;
    }

    public ProductResponseBuilder name(String name) {
        dto.setName(name);
        return this;
    }

    public ProductResponseBuilder quantity(BigDecimal quantity) {
        dto.setQuantity(quantity);
        return this;
    }

    public ProductResponseBuilder price(BigDecimal price) {
        dto.setPrice(price);
        return this;
    }

    public ProductResponseBuilder category(CategoryResponse category) {
        dto.setCategory(category);
        return this;
    }
}