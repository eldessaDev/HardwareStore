package com.hardware.store.service;

import com.hardware.store.dto.ProductDto;
import com.hardware.store.entity.Category;
import com.hardware.store.entity.Manufacturer;
import com.hardware.store.entity.Product;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.ProductMapper;
import com.hardware.store.repository.CategoryRepository;
import com.hardware.store.repository.ManufacturerRepository;
import com.hardware.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    // 1. El repositorio principal
    private final ProductRepository productRepository;

    // 2. Los repositorios auxiliares (para validar padres)
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    // 3. El Mapper
    private final ProductMapper productMapper = new ProductMapper();



    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = productMapper.productToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return productMapper.productToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // 1. Convertir datos básicos (Mapper)
        Product product = productMapper.dtoToEntity(productDto);

        // 2. BUSCAR LA CATEGORÍA (Usando el repo de categorías y el ID que viene en el DTO)
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));

        // 3. BUSCAR EL FABRICANTE (Usando el repo de manufacturers y el ID del DTO)
        Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId()).orElseThrow(()-> new ResourceNotFoundException("Manufacturer not found"));

        // 4. ASOCIAR (Meter los objetos encontrados dentro del producto)
        product.setCategory(category);
        product.setManufacturer(manufacturer);

        // 5. GUARDAR Y RETORNAR (Igual que hiciste en CategoryService)
        Product savedProduct = productRepository.save(product);
        return productMapper.productToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto productDto) {
        // 1. Buscar el producto existente (EL QUE TIENE EL ID)
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        // 2. Buscar la Categoria nueva
        Category existingCategory = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));

        // 3. Buscar el Manufacturer nuevo
        Manufacturer existingManufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                .orElseThrow(()-> new ResourceNotFoundException("Manufacturer not found"));

        // 4. Actualizar los campos SOBRE EL PRODUCTO EXISTENTE
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());

        // 5. Actualizar relaciones
        existingProduct.setCategory(existingCategory);
        existingProduct.setManufacturer(existingManufacturer);

        // 6. GUARDAR EL EXISTENTE (Aquí está la corrección)
        Product savedProduct = productRepository.save(existingProduct);

        return productMapper.productToDto(savedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        //1. find the product if it exists first
        Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        //2. delete product
        productRepository.delete(existingProduct);

    }
}
