package com.hardware.store.service;

import com.hardware.store.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProducts();
    ProductDto getProductById(Integer id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Integer id, ProductDto productDto);
    void deleteProduct(Integer id);


}
