package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.dto.product.ProductCreateUpdateRequest;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;

public class ProductCreateUpdateRequestBuilder {
    private ProductCreateUpdateRequest dto = new ProductCreateUpdateRequest();

    public static ProductCreateUpdateRequestBuilder aDto() {
        return new ProductCreateUpdateRequestBuilder();
    }

    public static ProductCreateUpdateRequestBuilder aDefaultDto() {
        ProductCreateUpdateRequestBuilder builder = new ProductCreateUpdateRequestBuilder();
        builder.dto = ProductCreateUpdateRequest.builder()
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .category(TestObjectData.aDefaultCategoryEntity().build().getName())
                .build();
        return builder;
    }

    public ProductCreateUpdateRequest build() {
        return dto;
    }

    public ProductCreateUpdateRequestBuilder name(String name) {
        dto.setName(name);
        return this;
    }

    public ProductCreateUpdateRequestBuilder quantity(BigDecimal quantity) {
        dto.setQuantity(quantity);
        return this;
    }

    public ProductCreateUpdateRequestBuilder price(BigDecimal price) {
        dto.setPrice(price);
        return this;
    }

    public ProductCreateUpdateRequestBuilder category(String category) {
        dto.setCategory(category);
        return this;
    }
}