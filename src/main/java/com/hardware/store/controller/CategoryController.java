package com.hardware.store.controller;

import com.hardware.store.dto.CategoryDto;
import com.hardware.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        // Le pedimos la comida al Chef
        List<CategoryDto> categoryDtoList = categoryService.findAllCategories();
        return ResponseEntity.ok(categoryDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto newCategoryDto  = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(newCategoryDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto>updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        CategoryDto newCategoryDto  = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(newCategoryDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
