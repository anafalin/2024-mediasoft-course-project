package team.mediasoft.warehouse.service;

import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.dto.category.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    String createCategory(CategoryDto dto);

    List<CategoryResponse> getAllCategories();

    void deleteCategoryById(UUID id);

    String updateCategoryById(CategoryDto dto, UUID id);
}