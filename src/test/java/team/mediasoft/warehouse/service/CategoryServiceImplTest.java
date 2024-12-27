package team.mediasoft.warehouse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotUniqueCategoryNameException;
import team.mediasoft.warehouse.mapper.AbstractCategoryMapper;
import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aCategoryDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryDto;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryEntity;
import static team.mediasoft.warehouse.datagenerator.TestObjectData.aDefaultCategoryResponse;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    CategoryServiceImpl underTest;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    AbstractCategoryMapper categoryMapper;

    @Test
    @DisplayName("create category | NotUniqueCategoryNameException | name is not unique")
    void givenCategoryWithNotUniqueName_whenCreateCategory_thenNotUniqueCategoryNameException() {
        // Given.
        String name = "new name";
        CategoryDto request = aDefaultCategoryDto().name(name).build();

        when(categoryRepository.existsByName(anyString()))
                .thenReturn(true);

        // When.
        assertThrows(NotUniqueCategoryNameException.class, () -> underTest.createCategory(request));
    }

    @Test
    @DisplayName("create category | successful created | name is unique")
    void givenCategoryWithNameUnique_whenCreateCategory_thenIdCreatedCategory() {
        // Given.
        CategoryDto request = aDefaultCategoryDto().build();
        Category category = aDefaultCategoryEntity().name(request.getName()).build();

        // When.
        when(categoryRepository.existsByName(anyString()))
                .thenReturn(false);

        when(categoryMapper.toCategory(any(CategoryDto.class)))
                .thenReturn(category);

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);


        String result = underTest.createCategory(request);

        // Then.
        verify(categoryRepository, times(1))
                .save(any(Category.class));

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).isNotEmpty()
        );
    }

    @Test
    @DisplayName("get all categories | result list is empty | categories do not exist")
    void givenRepositoryNoConsistRecords_whenGetAllCategories_thenEmptyList() {
        // Given.
        when(categoryRepository.findAll())
                .thenReturn(List.of());

        // When.
        List<CategoryResponse> result = underTest.getAllCategories();

        // Then.
        verify(categoryRepository, times(1))
                .findAll();

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("get all categories | result list is not empty | categories exist")
    void givenRepositoryConsistRecords_whenGetAllCategories_thenNotEmptyList() {
        // Given.
        String cat1 = "cat1";
        String cat2 = "cat2";
        when(categoryRepository.findAll())
                .thenReturn(List.of());

        when(categoryMapper.toCategoryResponseDtos(anyList()))
                .thenReturn(List.of(
                        aDefaultCategoryResponse()
                                .name(cat1)
                                .build(),
                        aDefaultCategoryResponse()
                                .name(cat2)
                                .build()));

        // When.
        List<CategoryResponse> result = underTest.getAllCategories();

        // Then.
        verify(categoryRepository, times(1)).findAll();

        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getName()).isNotNull(),
                () -> assertThat(result.get(0).getName()).isEqualTo(cat1),
                () -> assertThat(result.get(1).getName()).isNotNull(),
                () -> assertThat(result.get(1).getName()).isEqualTo(cat2)
        );
    }

    @Test
    @DisplayName("delete category by id | NotFoundCategoryException | category does not exist")
    void givenNotExistCategoryId_whenDeleteCategoryById_thenNoFoundElementExceptionExpected() {
        // Given.
        UUID id = UUID.randomUUID();

        when(categoryRepository.existsById(any()))
                .thenReturn(false);

        // When. AND THEN
        assertThrows(NotFoundCategoryException.class, () -> underTest.deleteCategoryById(id));
    }

    @Test
    @DisplayName("delete category by id | category successful deleted | category does not exist")
    void givenExistCategoryId_whenDeleteCategoryById_thenMessageCategorySuccessfulDeleted() {
        // Given.
        UUID id = UUID.randomUUID();

        when(categoryRepository.existsById(any()))
                .thenReturn(true);

        // When.
        underTest.deleteCategoryById(id);

        // Then.
        verify(categoryRepository, times(1))
                .deleteById(any());
    }

    @Test
    @DisplayName("update category by id | NotFoundCategoryException | category does not exist")
    void givenNoExistCategoryId_whenUpdateCategoryById_thenNoFoundElementExceptionExpected() {
        // Given.
        CategoryDto request = aCategoryDto().build();
        UUID id = UUID.randomUUID();

        // When.
        doReturn(Optional.empty())
                .when(categoryRepository)
                .findById(any());

        // THAN
        assertThrows(NotFoundCategoryException.class, () -> underTest.updateCategoryById(request, id));
    }

    @Test
    @DisplayName("update category by id | NotFoundCategoryException | category does not exist")
    void givenNoExistCategoryId_whenUpdateCategoryById_thenReturnUpdatableCategoryId() {
        // Given.
        CategoryDto request = aDefaultCategoryDto().name("New name").build();
        UUID id = request.getId();
        Category category = aDefaultCategoryEntity().id(id.toString()).build();
        Category savedCategory = aDefaultCategoryEntity().id(id.toString()).name(request.getName()).build();

        doReturn(Optional.of(category))
                .when(categoryRepository)
                .findById(any());

        doReturn(savedCategory)
                .when(categoryRepository)
                .save(any(Category.class));

        // When.
        String result = underTest.updateCategoryById(request, id);

        assertThat(result).isNotNull().isNotEmpty().isEqualTo(id.toString());
    }
}