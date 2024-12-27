package team.mediasoft.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team.mediasoft.warehouse.dto.category.CategoryCreateUpdateRequest;
import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotUniqueCategoryNameException;
import team.mediasoft.warehouse.mapper.AbstractCategoryMapper;
import team.mediasoft.warehouse.service.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aCategoryCreateUpdateRequest;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aCategoryDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryCreateUpdateRequest;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryResponse;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AbstractCategoryMapper categoryMapper;

    @MockBean
    CategoryServiceImpl categoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/categories addCategory | status is CREATED and returned id created category")
    void givenCorrectRequest_whenAddCategory_thenStatusIsCreatedAndReturnId() throws Exception {
        // Given
        CategoryCreateUpdateRequest request = aDefaultCategoryCreateUpdateRequest().build();
        CategoryDto categoryDto = aCategoryDto().name(request.getName()).build();
        String response = aDefaultCategoryDto().build().getId().toString();

        // When
        when(categoryMapper.toCategoryDto(any(CategoryCreateUpdateRequest.class)))
                .thenReturn(categoryDto);

        when(categoryService.createCategory(any(CategoryDto.class)))
                .thenReturn(response);

        // Then
        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("POST /api/categories addCategory | status is BAD REQUEST")
    void givenIncorrectRequestAndEmpty_whenAddCategory_thenStatusIsBadRequest() throws Exception {
        // Given
        CategoryCreateUpdateRequest request = aCategoryCreateUpdateRequest().build();

        // Then
        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("GET /api/categories getAllCategories | status is OK and response list is not empty")
    void givenCategoriesExist_whenGetAllCategories_thenResponseIsNotEmptyList() throws Exception {
        // Given.
        List<CategoryResponse> response = new ArrayList<>(List.of(
                aDefaultCategoryResponse().build(),
                aDefaultCategoryResponse().build()
        ));

        // When.
        when(categoryService.getAllCategories())
                .thenReturn(response);

        // Then.
        mvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].name").value(response.get(0).getName()))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/categories getAllCategories | status is OK amd response list is empty")
    void givenCategoriesNotExist_whenGetAllCategories_thenResponseIsEmptyList() throws Exception {
        // Given.
        List<CategoryResponse> response = new ArrayList<>();

        // When.
        when(categoryService.getAllCategories())
                .thenReturn(response);

        // Then.
        mvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andExpect(jsonPath("$.[0]").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/categories addCategory | status is CONFLICT and category is not created")
    void givenNotUniqueCategoryName_whenCreateCategory_thenStatusIsConflict() throws Exception {
        // Given.
        CategoryCreateUpdateRequest request = aDefaultCategoryCreateUpdateRequest().build();
        CategoryDto categoryDto = aDefaultCategoryDto().name(request.getName()).build();

        String errorMessage = "Category with name='%s' not found";

        // When.
        when(categoryMapper.toCategoryDto(any(CategoryCreateUpdateRequest.class)))
                .thenReturn(categoryDto);

        doThrow(new NotUniqueCategoryNameException(errorMessage.formatted(request.getName())))
                .when(categoryService)
                .createCategory(any(CategoryDto.class));

        // Then.
        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.time").isNotEmpty())
                .andExpect(jsonPath("$.exceptionName").isNotEmpty())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.message").value(errorMessage.formatted(request.getName())));
    }

    @Test
    @DisplayName("POST /api/categories addCategory | status is BadRequest and category is not created")
    void givenNoValidRequest_whenCreateCategory_thenStatusBadRequest() throws Exception {
        CategoryCreateUpdateRequest request = aDefaultCategoryCreateUpdateRequest().name("").build();

        mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("DELETE /api/categories/categories/{id} deleteCategory | status is OK and category is was deleted")
    void givenExistCategoryId_whenDeleteCategory_statusIsOk() throws Exception {
        String id = UUID.randomUUID().toString();

        mvc.perform(delete("/api/categories/%s".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/categories/{id} updateCategory " +
            "| category exist " +
            "| status is OK and category was updated")
    void givenExistCategoryIdAndCorrectObjectRequest_whenUpdateCategory_statusIsOk() throws Exception {
        // Given.
        String newName = "new Test name";
        String id = UUID.randomUUID().toString();
        CategoryCreateUpdateRequest request = aDefaultCategoryCreateUpdateRequest().name(newName).build();
        CategoryDto categoryDto = aDefaultCategoryDto().name(request.getName()).build();

        // When.
        when(categoryMapper.toCategoryDto(any(CategoryCreateUpdateRequest.class)))
                .thenReturn(categoryDto);

        doReturn(id)
                .when(categoryService)
                .updateCategoryById(any(CategoryDto.class), any(UUID.class));

        // Then.
        mvc.perform(patch("/api/categories/%s".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").value(id));
    }

    @Test
    @DisplayName("PATCH /api/categories/{id} updateCategory | category does not exist | status is NOT FOUND")
    void givenNoExistCategoryId_whenUpdateCategory_statusIsNotFound() throws Exception {
        // Given.
        String newName = "new Test name";
        String id = UUID.randomUUID().toString();
        CategoryCreateUpdateRequest request = aDefaultCategoryCreateUpdateRequest().name(newName).build();
        CategoryDto categoryDto = aDefaultCategoryDto().name(request.getName()).build();

        // When.
        when(categoryMapper.toCategoryDto(any(CategoryCreateUpdateRequest.class)))
                .thenReturn(categoryDto);
        doThrow(new NotFoundCategoryException("Category with id='%s' not exist".formatted(id)))
                .when(categoryService)
                .updateCategoryById(any(CategoryDto.class), any(UUID.class));

        // Then.
        mvc.perform(patch("/api/categories/%s".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category with id='%s' not exist".formatted(id)));
    }
}