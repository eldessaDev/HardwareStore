package com.hardware.store.service;

import com.hardware.store.dto.CategoryDto;
import com.hardware.store.entity.Category;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.CategoryMapper;
import com.hardware.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // 2. Lombok crea el constructor para el repositorio automáticamente
@Service // 1. Le dice a Spring: "Aquí hay lógica de negocio"
public class CategoryServiceImpl implements CategoryService {

    // HERRAMIENTA 1: El Repositorio (Inyectado por Spring/Lombok)
    private final CategoryRepository categoryRepository;

    // HERRAMIENTA 2: El Mapper (Manual, porque decidimos no usar @Component)
    private final CategoryMapper categoryMapper = new CategoryMapper();

    // METODO 1: Traer todas las categorías
    public List<CategoryDto> findAllCategories() {
        // 1. Buscamos la lista de Entidades en la DB
        List<Category> categoryList = categoryRepository.findAll();

        // 2. Preparamos una lista vacía para los DTOs
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        // 3. Recorremos y convertimos (Igual que hiciste en OrderMapper)
        for (Category category : categoryList) {
            CategoryDto categoryDto = categoryMapper.categoryToDto(category); // ¿Recuerdas cómo le pusiste al método en tu mapper?
            categoryDtoList.add(categoryDto);
        }
        // 4. Entregamos la lista de DTOs
        return categoryDtoList;

    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.categoryToDto(category);
    }


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // PASO 1: Convertir el DTO (que viene del cliente) a Entidad (para la DB)
        // Guardamos el resultado en una variable 'category'
        Category category = categoryMapper.categoryToEntity(categoryDto);

        // PASO 2: Guardar en la Base de Datos
        // IMPORTANTE: El repositorio nos devuelve una NUEVA instancia que YA TIENE EL ID generado.
        // La atrapamos en una variable 'savedCategory'.
        Category savedCategory = categoryRepository.save(category);

        // PASO 3: Convertir la entidad guardada (con ID) a DTO para responder
        return categoryMapper.categoryToDto(savedCategory);

    }

    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        // 1. BUSCAR: Primero traemos la categoría vieja de la base de datos
        // (Usamos el mismo truco del .orElseThrow que ya aprendiste)
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // 3. GUARDAR: Guardamos los cambios en la base de datos, atrapar de nuevo el objeto para evitar errores en db
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);

    }
}
