package com.hardware.store.service;

import com.hardware.store.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAllCategories();
    CategoryDto getCategoryById(Integer id);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Integer id, CategoryDto categoryDto);
    void deleteCategory(Integer id);


}
