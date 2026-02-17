package com.hardware.store.controller;

import com.hardware.store.dto.ProductDto;
import com.hardware.store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //Le dice a Spring "Esta clase va a responder peticiones web (JSON)"
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    //endPoints
    // GET All localhost:8080/api/products
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        // Le pedimos la comida al Chef
        List<ProductDto> products = productService.getProducts();
        // Se la entregamos al cliente con un código 200 OK
        return ResponseEntity.ok(products);
    }

    // GET By Id localhost:8080/api/products/5
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id){
        //Creamos un objeto de productDto para buscar el id
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){//Recibe el JSON
        ProductDto productDto1 = productService.createProduct(productDto);//llama al servicio para crearlo
        //devolvemos el codigo 201(Created)
        return ResponseEntity.ok(productDto1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDto productDto){
        // Capturamos el producto actualizado que nos devuelve el servicio
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        // Se lo mostramos al cliente
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return  ResponseEntity.noContent().build();
    }
}
