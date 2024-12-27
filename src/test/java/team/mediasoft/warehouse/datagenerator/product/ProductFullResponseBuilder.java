package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.dto.product.ProductFullResponse;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;

public class ProductFullResponseBuilder {
    private ProductFullResponse dto = new ProductFullResponse();

    public static ProductFullResponseBuilder aDto() {
        return new ProductFullResponseBuilder();
    }

    public static ProductFullResponseBuilder aDefaultDto() {
        ProductFullResponseBuilder builder = new ProductFullResponseBuilder();
        builder.dto = ProductFullResponse.builder()
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .category(TestObjectData.aCategoryResponse().getDto())
                .build();
        return builder;
    }

    public ProductFullResponse build() {
        return dto;
    }

    public ProductFullResponseBuilder name(String name) {
        dto.setName(name);
        return this;
    }

    public ProductFullResponseBuilder quantity(BigDecimal quantity) {
        dto.setQuantity(quantity);
        return this;
    }

    public ProductFullResponseBuilder price(BigDecimal price) {
        dto.setPrice(price);
        return this;
    }

    public ProductFullResponseBuilder category(CategoryResponse category) {
        dto.setCategory(category);
        return this;
    }
}