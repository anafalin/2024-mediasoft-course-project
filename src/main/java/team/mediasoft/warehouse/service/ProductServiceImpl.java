package team.mediasoft.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;
import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.model.Product;
import team.mediasoft.warehouse.exception.NotAvailableProductException;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotFoundProductException;
import team.mediasoft.warehouse.exception.NotUniqueProductNameException;
import team.mediasoft.warehouse.mapper.AbstractProductMapper;
import team.mediasoft.warehouse.repository.CategoryRepository;
import team.mediasoft.warehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final AbstractProductMapper productMapper;

    @Override
    @Transactional
    public String createProduct(ProductDto dto) {
        if (productRepository.existsByName(dto.getName())) {
            throw new NotUniqueProductNameException("Product with name = '%s' already exists".formatted(dto.getName()));
        }

        Category category = getCategoryByNameIfExist(dto);

        Product newProduct = productMapper.toProduct(dto, category);

        Product savedProduct = productRepository.save(newProduct);

        return savedProduct.getId().toString();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductInfoResponse getProductById(UUID id) {
        Product product = getProductByIdIfExist(id);

        if (!product.getIsAvailable()) {
            throw new NotAvailableProductException("Product with id = '%s' not exist".formatted(id));
        }

        return productMapper.toProductInfoDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInfoResponse> getProducts(Pageable pageRequest) {
        Page<Product> products = productRepository.findAll(pageRequest);
        return productMapper.toProductInfoDtos(products.get().toList());
    }

    @Override
    @Transactional
    public void deleteProductById(UUID id) {
        Product product = getProductByIdIfExist(id);

        product.setIsAvailable(false);

        productRepository.save(product);
    }

    @Override
    @Transactional
    public String updateProductById(ProductDto dto, UUID id) {
        Product product = getProductByIdIfExist(id);

        Category category = getCategoryByNameIfExist(dto);

        productMapper.update(product, dto, category);

        productRepository.save(product);

        return product.getId().toString();
    }

    private Category getCategoryByNameIfExist(ProductDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(dto.getCategory());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundCategoryException("Category with name='%s' not found".formatted(dto.getCategory()));
        }
        return optionalCategory.get();
    }

    private Product getProductByIdIfExist(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundProductException("Product with id = '%s' not found".formatted(id)));
    }
}