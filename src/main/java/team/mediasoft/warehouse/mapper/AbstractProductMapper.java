package team.mediasoft.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import team.mediasoft.warehouse.dto.product.ProductCreateUpdateRequest;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.dto.product.ProductFullResponse;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;
import team.mediasoft.warehouse.dto.product.ProductResponse;
import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.model.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AbstractProductMapper {
    @Mapping(target = "isAvailable", expression = "java(true)")
    public abstract ProductDto toProductDto(ProductCreateUpdateRequest dto);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "dto.name")
    public abstract Product toProduct(ProductDto dto, Category category);

    public abstract ProductResponse toProductResponseDto(ProductInfoResponse dto);

    public abstract ProductInfoResponse toProductInfoDto(Product product);

    public abstract List<ProductInfoResponse> toProductInfoDtos(List<Product> products);

    public abstract List<ProductResponse> toProductResponseDtos(List<ProductInfoResponse> dtos);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "source.name")
    public abstract void update(@MappingTarget Product product, ProductDto source, Category category);

    public abstract List<ProductFullResponse> toProductFullResponseDtos(List<ProductInfoResponse> dtos);
}