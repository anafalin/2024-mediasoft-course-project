package team.mediasoft.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.mediasoft.warehouse.dto.product.ProductCreateUpdateRequest;
import team.mediasoft.warehouse.dto.product.ProductFullResponse;
import team.mediasoft.warehouse.dto.product.ProductResponse;
import team.mediasoft.warehouse.mapper.AbstractProductMapper;
import team.mediasoft.warehouse.service.ProductServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    private final AbstractProductMapper productMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody @Valid ProductCreateUpdateRequest request) {
        return productService.createProduct(productMapper.toProductDto(request));
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable UUID id) {
        return productMapper.toProductResponseDto(productService.getProductById(id));
    }

    @GetMapping
    public List<ProductFullResponse> getProducts(Pageable pageRequest) {
        return productMapper.toProductFullResponseDtos(productService.getProducts(pageRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
    }

    @PatchMapping("/{id}")
    public String updateProduct(@PathVariable UUID id, @RequestBody ProductCreateUpdateRequest request) {
        return productService.updateProductById(productMapper.toProductDto(request), id);
    }
}