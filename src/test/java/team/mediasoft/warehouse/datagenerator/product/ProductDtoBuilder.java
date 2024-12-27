package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.dto.product.ProductCreateUpdateRequest;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;

public class ProductDtoBuilder {
    private ProductDto dto = new ProductDto();

    public static ProductDtoBuilder aDto() {
        return new ProductDtoBuilder();
    }

    public static ProductDtoBuilder aDefaultDto() {
        ProductDtoBuilder builder = new ProductDtoBuilder();
        builder.dto = ProductDto.builder()
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .isAvailable(true)
                .category(TestObjectData.aCategoryEntity().build().getName())
                .build();
        return builder;
    }

    public ProductDto build() {
        return dto;
    }

    public ProductDtoBuilder withFields(ProductCreateUpdateRequest dto) {
        this.dto.setName(dto.getName());
        this.dto.setCategory(dto.getCategory());
        this.dto.setPrice(dto.getPrice());
        this.dto.setQuantity(dto.getQuantity());
        this.dto.setArticleNumber(dto.getArticleNumber());
        this.dto.setDescription(dto.getDescription());
        return this;
    }

    public ProductDtoBuilder name(String name) {
        dto.setName(name);
        return this;
    }

    public ProductDtoBuilder quantity(BigDecimal quantity) {
        dto.setQuantity(quantity);
        return this;
    }

    public ProductDtoBuilder price(BigDecimal price) {
        dto.setPrice(price);
        return this;
    }

    public ProductDtoBuilder category(String category) {
        dto.setCategory(category);
        return this;
    }

    public ProductDtoBuilder isAvailable(boolean isAvailable) {
        dto.setIsAvailable(isAvailable);
        return this;
    }
}