package team.mediasoft.warehouse.datagenerator;

import team.mediasoft.warehouse.datagenerator.category.CategoryCreateUpdateRequestBuilder;
import team.mediasoft.warehouse.datagenerator.category.CategoryDtoBuilder;
import team.mediasoft.warehouse.datagenerator.category.CategoryEntityBuilder;
import team.mediasoft.warehouse.datagenerator.category.CategoryResponseBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductCreateUpdateRequestBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductDtoBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductEntityBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductFullResponseBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductInfoResponseBuilder;
import team.mediasoft.warehouse.datagenerator.product.ProductResponseBuilder;

public class TestObjectData {
    // Category
    public static CategoryEntityBuilder aDefaultCategoryEntity() {
        return CategoryEntityBuilder.aDefaultEntity();
    }

    public static CategoryEntityBuilder aCategoryEntity() {
        return CategoryEntityBuilder.aEntity();
    }

    // CategoryCreateRequest
    public static CategoryCreateUpdateRequestBuilder aDefaultCategoryCreateUpdateRequest() {
        return CategoryCreateUpdateRequestBuilder.aDefaultDto();
    }

    public static CategoryCreateUpdateRequestBuilder aCategoryCreateUpdateRequest() {
        return CategoryCreateUpdateRequestBuilder.aDto();
    }

    // CategoryDto
    public static CategoryDtoBuilder aDefaultCategoryDto() {
        return CategoryDtoBuilder.aDefaultDto();
    }

    public static CategoryDtoBuilder aCategoryDto() {
        return CategoryDtoBuilder.aDto();
    }

    // CategoryResponseDto
    public static CategoryResponseBuilder aDefaultCategoryResponse() {
        return CategoryResponseBuilder.aDefaultDto();
    }

    public static CategoryResponseBuilder aCategoryResponse() {
        return CategoryResponseBuilder.aDto();
    }

    // Product
    public static ProductEntityBuilder aDefaultProductEntity() {
        return ProductEntityBuilder.aDefaultEntity();
    }


    // ProductDto
    public static ProductDtoBuilder aDefaultProductDto() {
        return ProductDtoBuilder.aDefaultDto();
    }

    // ProductCreateUpdateRequestDto
    public static ProductCreateUpdateRequestBuilder aDefaultProductCreateUpdateRequest() {
        return ProductCreateUpdateRequestBuilder.aDefaultDto();
    }

    // ProductInfoDto
    public static ProductInfoResponseBuilder aDefaultProductInfoResponse() {
        return ProductInfoResponseBuilder.aDefaultDto();
    }

    public static ProductInfoResponseBuilder aProductInfoResponse() {
        return ProductInfoResponseBuilder.aDto();
    }

    // ProductResponseDto
    public static ProductResponseBuilder aDefaultProductResponse() {
        return ProductResponseBuilder.aDefaultDto();
    }


    // ProductFullResponseDto
    public static ProductFullResponseBuilder aDefaultProductFullResponse() {
        return ProductFullResponseBuilder.aDefaultDto();
    }

}