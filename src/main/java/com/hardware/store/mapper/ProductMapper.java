package com.hardware.store.mapper;

import com.hardware.store.dto.ProductDto;
import com.hardware.store.entity.Product;

public class ProductMapper {

    public ProductDto productToDto(Product product){

            if (product == null){
                return null;
            }

        ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setStock(product.getStock());

            if (product.getCategory() != null){
                productDto.setCategoryId(product.getCategory().getId());
            }

            if (product.getManufacturer() != null){
                productDto.setManufacturerId(product.getManufacturer().getId());
            }

            return productDto;
    }

    public Product dtoToEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        return product;
    }


}
