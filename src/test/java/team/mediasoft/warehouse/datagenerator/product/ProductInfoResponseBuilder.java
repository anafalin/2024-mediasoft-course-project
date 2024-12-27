package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductInfoResponseBuilder {
    private ProductInfoResponse dto = new ProductInfoResponse();

    public static ProductInfoResponseBuilder aDto() {
        return new ProductInfoResponseBuilder();
    }

    public static ProductInfoResponseBuilder aDefaultDto() {
        ProductInfoResponseBuilder builder = new ProductInfoResponseBuilder();
        builder.dto = ProductInfoResponse.builder()
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .isAvailable(true)
                .dateCreation(LocalDate.now())
                .category(TestObjectData.aCategoryResponse().build())
                .build();
        return builder;
    }

    public ProductInfoResponse build() {
        return dto;
    }

    public ProductInfoResponseBuilder name(String name) {
        dto.setName(name);
        return this;
    }

    public ProductInfoResponseBuilder quantity(BigDecimal quantity) {
        dto.setQuantity(quantity);
        return this;
    }

    public ProductInfoResponseBuilder price(BigDecimal price) {
        dto.setPrice(price);
        return this;
    }

    public ProductInfoResponseBuilder dateTimeLastEdit(LocalDateTime dateTimeLastEdit) {
        dto.setDateTimeLastEdit(dateTimeLastEdit);
        return this;
    }

    public ProductInfoResponseBuilder dateCreation(LocalDate dateCreation) {
        dto.setDateCreation(dateCreation);
        return this;
    }

    public ProductInfoResponseBuilder category(CategoryResponse category) {
        dto.setCategory(category);
        return this;
    }
}