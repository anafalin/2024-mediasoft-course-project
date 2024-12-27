package team.mediasoft.warehouse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;
import team.mediasoft.warehouse.exception.NotAvailableProductException;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotFoundProductException;
import team.mediasoft.warehouse.exception.NotUniqueProductNameException;
import team.mediasoft.warehouse.mapper.AbstractProductMapper;
import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.model.Product;
import team.mediasoft.warehouse.repository.CategoryRepository;
import team.mediasoft.warehouse.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryEntity;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductEntity;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductInfoResponse;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    AbstractProductMapper productMapper;

    @InjectMocks
    ProductServiceImpl underTest;

    @Test
    @DisplayName("create product | NotUniqueProductNameException | category does not exist")
    void givenNotExistCategoryName_whenCreateProduct_thenNotUniqueProductNameExceptionExpected() {
        // Given.
        ProductDto request = aDefaultProductDto().category("not exist category name").build();

        // When.
        when(productRepository.existsByName(anyString()))
                .thenReturn(true);

        // Then.
        assertThrows(NotUniqueProductNameException.class, () -> underTest.createProduct(request));
    }

    @Test
    @DisplayName("create product | NotFoundCategoryException | category does not exist")
    void givenNotExistCategoryName_whenCreateProduct_thenNotFoundCategoryExceptionExpected() {
        // Given.
        ProductDto request = aDefaultProductDto().category("not exist category name").build();

        // When.
        when(productRepository.existsByName(anyString()))
                .thenReturn(false);

        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        // Then.
        assertThrows(NotFoundCategoryException.class, () -> underTest.createProduct(request));
    }

    @Test
    @DisplayName("create product | product was created | category exist")
    void givenCorrectProduct_whenCreateProduct_thenIdProductCreated() {
        // Given.
        String category = "Category of test";
        Category categoryEntity = aDefaultCategoryEntity().name(category).build();
        ProductDto request = aDefaultProductDto().category(category).build();
        Product product = aDefaultProductEntity().withCategory(categoryEntity).build();

        // When.
        when(productRepository.existsByName(anyString()))
                .thenReturn(false);

        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.of(categoryEntity));

        when(productMapper.toProduct(any(ProductDto.class), any(Category.class)))
                .thenReturn(product);

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        // Then.
        String result = underTest.createProduct(request);

        verify(productRepository, times(1)).save(any(Product.class));

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("delete product by id | NotFoundProductException | product does not exist")
    void givenNoExistIdProduct_whenDeleteProductById_thenNoFoundElementExceptionExpected() {
        // Given.
        UUID id = UUID.randomUUID();

        // When. 
        doReturn(Optional.empty())
                .when(productRepository)
                .findById(any());

        // Then.
        assertThrows(NotFoundProductException.class, () -> underTest.deleteProductById(id));
    }

    @Test
    @DisplayName("delete product by id | product successful deleted | product does not exist")
    void givenExistIdProduct_whenDeleteProductById_thenMessageProductSuccessfulDeleted() {
        // Given.
        UUID id = UUID.randomUUID();

        // When.
        doReturn(Optional.of(aDefaultProductEntity().build()))
                .when(productRepository)
                .findById(any());

        // Then.
        underTest.deleteProductById(id);

        verify(productRepository, times(1))
                .save(any());
    }


    @Test
    @DisplayName("update product by id | NoFoundElementException | product does not exist")
    void givenNotExistIdProduct_whenUpdateProductById_thenNoFoundElementExceptionExpected() {
        // Given.
        UUID id = UUID.randomUUID();
        ProductDto request = aDefaultProductDto().build();

        // When. 
        doReturn(Optional.empty())
                .when(productRepository)
                .findById(any());

        // Then.
        assertThrows(NotFoundProductException.class, () -> underTest.updateProductById(request, id));
    }

    @Test
    @DisplayName("update product by id | product was updated | product  exist")
    void givenExistIdProduct_whenUpdateProductById_thenNoFoundElementExceptionExpected() {
        // Given.
        UUID id = UUID.randomUUID();
        String productName = "expectedName";
        String categoryName = "expectedCategoryName";
        Category category = aDefaultCategoryEntity().name(categoryName).build();
        Product product = aDefaultProductEntity()
                .withId(id.toString())
                .withName(productName)
                .withCategory(category)
                .build();
        ProductDto request = aDefaultProductDto()
                .name(productName)
                .category(categoryName)
                .build();
        doReturn(Optional.of(product))
                .when(productRepository)
                .findById(any());

        // When.
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        // Then.
        String result = underTest.updateProductById(request, id);

        verify(productRepository, times(1))
                .save(any(Product.class));

        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(id.toString());
    }

    @Test
    @DisplayName("get product by id | NoFoundElementException | product does not exist")
    void givenNotExistProductId_whenGetProductById_thenNoFoundElementExceptionExpected() {
        // When.
        doThrow(NotFoundProductException.class)
                .when(productRepository)
                .findById(any());

        // Then.
        assertThrows(NotFoundProductException.class, () -> underTest.getProductById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("get product by id | NotAvailableProductException | product is not exist")
    void givenProductIdWhichIsNotAvailable_whenGetProductById_thenNoFoundElementExceptionExpected() {
        // Given.
        Product product = aDefaultProductEntity().withIsAvailable(false).build();

        // When.
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));

        // Then.
        assertThrows(NotAvailableProductException.class, () -> underTest.getProductById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("get product by id | productWasFound | product  exist")
    void givenExistProductId_whenGetProductById_thenProductReturned() {
        // Given.
        String name = "Oil";

        Product product = aDefaultProductEntity()
                .withName(name)
                .build();
        ProductInfoResponse productDto = aDefaultProductInfoResponse()
                .name(name)
                .build();

        // When.
        doReturn(Optional.of(product))
                .when(productRepository)
                .findById(any());

        doReturn(productDto)
                .when(productMapper)
                .toProductInfoDto(any(Product.class));

        // Then.
        ProductInfoResponse result = underTest.getProductById(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    @DisplayName("get products | empty result list | products do not exist")
    void getProducts_thenEmptyProductList() {
        // Given.
        List<ProductInfoResponse> response = List.of();
        Page<Product> products = Page.empty();

        // When.
        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(products);

        doReturn(response)
                .when(productMapper)
                .toProductInfoDtos(any());

        // Then.
        List<ProductInfoResponse> result = underTest.getProducts(
                PageRequest.of(0, 2,
                        Sort.by(Sort.Direction.DESC, "name")));
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("get products | not empty result list | products exist")
    void getProducts_thenNotEmptyProductList() {
        // Given.
        ProductInfoResponse pDto1 = aDefaultProductInfoResponse().name("Product N").build();
        ProductInfoResponse pDto2 = aDefaultProductInfoResponse().name("Product Z").build();
        ProductInfoResponse pDto3 = aDefaultProductInfoResponse().name("Product A").build();

        List<ProductInfoResponse> response = new ArrayList<>(List.of(pDto1, pDto2, pDto3))
                .stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .toList();

        Page<Product> products = new PageImpl<>(List.of(
                aDefaultProductEntity().withName(pDto1.getName()).build(),
                aDefaultProductEntity().withName(pDto2.getName()).build(),
                aDefaultProductEntity().withName(pDto3.getName()).build()
        ));

        // When.
        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(products);

        doReturn(response)
                .when(productMapper)
                .toProductInfoDtos(any());

        // Then.
        List<ProductInfoResponse> result = underTest.getProducts(
                PageRequest.of(0, 2,
                        Sort.by(Sort.Direction.DESC, "name")));

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo(pDto3.getName());
    }
}