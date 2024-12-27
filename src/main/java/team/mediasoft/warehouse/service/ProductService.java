package team.mediasoft.warehouse.service;

import org.springframework.data.domain.Pageable;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    String createProduct(ProductDto dto);

    List<ProductInfoResponse> getProducts(Pageable pageRequest);

    ProductInfoResponse getProductById(UUID id);

    void deleteProductById(UUID id);

    String updateProductById(ProductDto dto, UUID id);
}