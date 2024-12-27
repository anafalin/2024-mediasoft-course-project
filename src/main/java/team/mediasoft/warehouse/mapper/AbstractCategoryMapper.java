package team.mediasoft.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import team.mediasoft.warehouse.dto.category.CategoryCreateUpdateRequest;
import team.mediasoft.warehouse.dto.category.CategoryDto;
import team.mediasoft.warehouse.dto.category.CategoryResponse;
import team.mediasoft.warehouse.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AbstractCategoryMapper {

    public abstract CategoryDto toCategoryDto(CategoryCreateUpdateRequest request);

    public abstract Category toCategory(CategoryDto dto);

    public abstract List<CategoryResponse> toCategoryResponseDtos(List<Category> categories);

    public abstract void update(@MappingTarget Category category, CategoryDto source);
}