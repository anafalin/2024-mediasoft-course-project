package team.mediasoft.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.model.Category;
import team.mediasoft.warehouse.exception.NotFoundCategoryException;
import team.mediasoft.warehouse.exception.NotUniqueCategoryNameException;
import team.mediasoft.warehouse.mapper.AbstractCategoryMapper;
import team.mediasoft.warehouse.repository.CategoryRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final AbstractCategoryMapper categoryMapper;

    @Override
    @Transactional
    public String createCategory(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new NotUniqueCategoryNameException("Category with name '%s' already exists".formatted(dto.getName()));
        }

        Category newCategory = categoryMapper.toCategory(dto);

        Category savedCategory = categoryRepository.save(newCategory);

        return savedCategory.getId().toString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toCategoryResponseDtos(categoryRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteCategoryById(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundCategoryException("Category with id='%s' not found".formatted(id));
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String updateCategoryById(CategoryDto dto, UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundCategoryException("Category with id='%s' not found".formatted(id)));

        categoryMapper.update(category, dto);

        Category savedCategory = categoryRepository.save(category);

        return savedCategory.getId().toString();
    }
}