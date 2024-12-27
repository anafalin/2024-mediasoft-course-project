package team.mediasoft.warehouse.datagenerator.product;

import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.model.Product;
import team.mediasoft.warehouse.datagenerator.TestObjectData;
import team.mediasoft.warehouse.datagenerator.RandomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductEntityBuilder {
    private Product product = new Product();

    public static ProductEntityBuilder aEntity() {
        return new ProductEntityBuilder();
    }

    public static ProductEntityBuilder aDefaultEntity() {
        ProductEntityBuilder builder = new ProductEntityBuilder();
        builder.product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product Test_Name " + RandomService.getRandomInt())
                .articleNumber(String.valueOf(RandomService.getRandomArticle()))
                .description("Description")
                .price(BigDecimal.valueOf(RandomService.getRandomPrice()))
                .quantity(BigDecimal.valueOf(RandomService.getRandomInt()))
                .isAvailable(true)
                .dateCreation(LocalDate.now())
                .category(TestObjectData.aCategoryEntity().build())
                .build();
        return builder;
    }

    public Product build() {
        return product;
    }

    public ProductEntityBuilder withId(String id) {
        product.setId(UUID.fromString(id));
        return this;
    }

    public ProductEntityBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductEntityBuilder withQuantity(BigDecimal quantity) {
        product.setQuantity(quantity);
        return this;
    }

    public ProductEntityBuilder withPrice(BigDecimal price) {
        product.setPrice(price);
        return this;
    }

    public ProductEntityBuilder withDateTimeLastEdit(LocalDateTime dateTimeLastEdit) {
        product.setDateTimeLastEdit(dateTimeLastEdit);
        return this;
    }

    public ProductEntityBuilder withDateCreation(LocalDate dateCreation) {
        product.setDateCreation(dateCreation);
        return this;
    }

    public ProductEntityBuilder withCategory(Category category) {
        product.setCategory(category);
        return this;
    }

    public ProductEntityBuilder withIsAvailable(boolean isAvailable) {
        product.setIsAvailable(isAvailable);
        return this;
    }
}