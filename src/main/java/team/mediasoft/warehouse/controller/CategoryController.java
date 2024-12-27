package team.mediasoft.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import team.mediasoft.warehouse.dto.category.CategoryCreateUpdateRequest;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.mapper.AbstractCategoryMapper;
import team.mediasoft.warehouse.service.CategoryServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    private final AbstractCategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addCategory(@RequestBody @Valid CategoryCreateUpdateRequest request) {
        return categoryService.createCategory(categoryMapper.toCategoryDto(request));
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
    }

    @PatchMapping("/{id}")
    public String updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryCreateUpdateRequest request) {
        return categoryService.updateCategoryById(categoryMapper.toCategoryDto(request), id);
    }
}