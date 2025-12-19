package com.hardware.store.mapper;

import com.hardware.store.dto.CategoryDto;
import com.hardware.store.entity.Category;

public class CategoryMapper {

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    public Category categoryToEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
