package team.mediasoft.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team.mediasoft.warehouse.dto.product.ProductCreateUpdateRequest;
import team.mediasoft.warehouse.dto.product.ProductDto;
import team.mediasoft.warehouse.dto.product.ProductInfoResponse;
import team.mediasoft.warehouse.dto.product.ProductResponse;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotFoundProductException;
import team.mediasoft.warehouse.mapper.AbstractProductMapper;
import team.mediasoft.warehouse.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductCreateUpdateRequest;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductEntity;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductFullResponse;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductInfoResponse;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultProductResponse;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aProductInfoResponse;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AbstractProductMapper productMapper;

    @MockBean
    ProductServiceImpl productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/products addProduct | status is CREATED and created product is returned")
    void givenValidRequest_whenCreateProduct_thenStatusCreated() throws Exception {
        // Given.
        ProductCreateUpdateRequest request = aDefaultProductCreateUpdateRequest().build();
        ProductDto dto = aDefaultProductDto().withFields(request).build();
        String response = aDefaultProductEntity().build().getId().toString();

        // When.
        when(productMapper.toProductDto(any(ProductCreateUpdateRequest.class)))
                .thenReturn(dto);

        when(productService.createProduct(any(ProductDto.class)))
                .thenReturn(response);

        // Then.
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isString());
    }

    @Test
    @DisplayName("DELETE /api/products/{id} deleteProduct | status is OK and product is was deleted")
    void givenExistProductId_whenDeleteProduct_statusIsOk() throws Exception {
        String id = UUID.randomUUID().toString();

        mvc.perform(delete("/api/products/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/products/{id} deleteProduct " +
            "| product not exist " +
            "| status is NOT FOUND and product is was not deleted")
    void givenNotExistProductId_whenDeleteProduct_statusIsNotFound() throws Exception {
        // Given.
        String id = UUID.randomUUID().toString();

        // When.
        doThrow(new NotFoundProductException("Product with id='%s' not found".formatted(id)))
                .when(productService)
                .deleteProductById(any(UUID.class));

        // Then.
        mvc.perform(delete("/api/products/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Product with id='%s' not found".formatted(id)));
    }

    @Test
    @DisplayName("PATCH /api/products/{id} updateProduct | product does not exist | status is NOT FOUND |")
    void givenNotExistProductId_whenUpdateProduct_statusIsNotFound() throws Exception {
        // Given.
        UUID id = UUID.randomUUID();
        ProductCreateUpdateRequest request = ProductCreateUpdateRequest.builder().quantity(BigDecimal.valueOf(7)).build();
        ProductDto dto = aDefaultProductDto().withFields(request).build();

        // When.
        doReturn(dto)
                .when(productMapper)
                .toProductDto(any(ProductCreateUpdateRequest.class));
        doThrow(new NotFoundProductException("Product with id='%s' not exist".formatted(id)))
                .when(productService)
                .updateProductById(any(ProductDto.class), any(UUID.class));

        // Then.
        mvc.perform(patch("/api/products/%s".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Product with id='%s' not exist".formatted(id)));
    }

    @Test
    @DisplayName("PATCH /api/products/{id} updateProduct | product exist | status is OK and product was updated")
    void givenExistProductIdAndCorrectObjectRequest_whenUpdateProduct_statusIsOk() throws Exception {
        // Given.
        BigDecimal newCount = BigDecimal.valueOf(7L);
        String id = UUID.randomUUID().toString();
        ProductCreateUpdateRequest request = aDefaultProductCreateUpdateRequest().quantity(newCount).build();
        ProductDto dto = aDefaultProductDto().withFields(request).build();

        // When.
        doReturn(dto)
                .when(productMapper)
                .toProductDto(any(ProductCreateUpdateRequest.class));

        doReturn(id)
                .when(productService)
                .updateProductById(any(ProductDto.class), any(UUID.class));

        // Then.
        mvc.perform(patch("/api/products/%s".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/products addProduct | status is NOT FOUND and returned exception")
    void givenProductWithCategoryNotExist_whenCreateProduct_thenStatusIsNotFound() throws Exception {
        // Given.
        ProductCreateUpdateRequest request = aDefaultProductCreateUpdateRequest().category("unknown category").build();
        ProductDto dto = aDefaultProductDto().withFields(request).build();

        // When.
        doReturn(dto)
                .when(productMapper)
                .toProductDto(any(ProductCreateUpdateRequest.class));
        doThrow(new NotFoundCategoryException(
                "Category with name='%s' not found".formatted(request.getCategory())))
                .when(productService).createProduct(any(ProductDto.class));

        // Then.
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.message")
                        .value("Category with name='%s' not found".formatted(request.getCategory())));
    }

    @Test
    @DisplayName("POST /api/products addProduct | status is BAD REQUEST and product is not created")
    void givenEmptyProductName_whenCreateProduct_thenStatusIsBadRequest() throws Exception {
        ProductCreateUpdateRequest request = aDefaultProductCreateUpdateRequest().name("").build();

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/products getProducts | status is OK and response list is not empty")
    void getProducts_whenExistProducts_thenStatusIsOkAndNotEmptyResponseList() throws Exception {
        // Given.
        Pageable pageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "Name"));

        // When.
        when(productService.getProducts(any(Pageable.class)))
                .thenReturn(List.of());

        // Then.
        mvc.perform(get("/api/products?"
                        .concat("page="
                                .concat(String.valueOf(pageable.getPageNumber()))
                                .concat("&size=")
                                .concat(String.valueOf(pageable.getPageSize()))
                                .concat("&SORT=")
                                .concat(pageable.getSort().toString()))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/products getProducts | status is OK and response list is empty")
    void getProducts_whenNotExistProducts_thenStatusIsOkAndEmptyResponseList() throws Exception {
        // Given.
        Pageable pageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "Name"));

        // When.
        when(productService.getProducts(any(Pageable.class)))
                .thenReturn(List.of(aProductInfoResponse().build(), aProductInfoResponse().build()));

        when(productMapper.toProductFullResponseDtos(anyList()))
                .thenReturn(List.of(aDefaultProductFullResponse().build(), aDefaultProductFullResponse().build()));

        // Then.
        mvc.perform(get("/api/products?"
                        .concat("page="
                                .concat(String.valueOf(pageable.getPageNumber()))
                                .concat("&size=")
                                .concat(String.valueOf(pageable.getPageSize()))
                                .concat("&SORT=")
                                .concat(pageable.getSort().toString()))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists());
    }

    @Test
    @DisplayName("GET /api/products/{id} getProductById | product exist | status is OK and returned product")
    void givenCorrectId_whenGetProductById_statusIsOkAndReturnedProduct() throws Exception {
        // Given.
        ProductInfoResponse dto = aDefaultProductInfoResponse().build();
        ProductResponse response = aDefaultProductResponse().name(dto.getName()).build();

        // When.
        when(productService.getProductById(any(UUID.class)))
                .thenReturn(dto);

        doReturn(response)
                .when(productMapper)
                .toProductResponseDto(any(ProductInfoResponse.class));

        // Then.
        mvc.perform(get("/api/products/%s".formatted(response.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("GET /api/products/{id} getProductById | product not exist | status is NOT FOUND")
    void givenIncorrectId_whenGetProductById_statusIsNotFound() throws Exception {
        //GIVEN
        UUID id = UUID.randomUUID();

        // When.
        doThrow(new NotFoundCategoryException(
                "Product with id='%s' not found".formatted(id)))
                .when(productService).getProductById(any(UUID.class));

        // Then.
        mvc.perform(get("/api/products/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.exceptionName").exists());
    }
}